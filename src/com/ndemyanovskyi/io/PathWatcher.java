/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ndemyanovskyi.io;


import com.ndemyanovskyi.observing.Listeners;
import com.ndemyanovskyi.observing.Observable;
import com.ndemyanovskyi.throwable.RuntimeIOException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
 
public class PathWatcher extends Thread implements Observable {
    
    private final Path path;
    private final WatchService watchService;
    private final ObservableList<String> fileNames = FXCollections.observableArrayList();
    private final ObservableList<String> fileNamesUnmodifiable = FXCollections.unmodifiableObservableList(fileNames);
    
    private Listeners<Observable.Listener> listeners;
    
    private boolean disposed = false;
    private boolean started = false;

    protected PathWatcher(Path path) {
	super("PathWatcher [" + (path = path.toAbsolutePath()) + "]");
        
	this.path = path;
	
	try {
	    this.watchService = path.getFileSystem().newWatchService();
	    path.register(watchService, ENTRY_CREATE, ENTRY_MODIFY, ENTRY_DELETE);
	    Files.list(path).forEach(p -> {
		fileNames.add(p.getName(p.getNameCount() - 1).toString());
	    });
	} catch(IOException ex) {
	    throw new RuntimeIOException(ex);
	}
    }

    public static PathWatcher of(String path) {
	return of(Paths.get(path));
    }
    
    public static PathWatcher of(Path path) {
	return new PathWatcher(path);
    }

    public Path getPath() {
	return path;
    }

    public ObservableList<String> getFileNames() {
	return fileNamesUnmodifiable;
    }
    
    protected void onCreate(String fileName) {
	fileNames.add(fileName);
	if(listeners != null) {
	    listeners.sub(Listener.class).
		    notifyChange(l -> l.onCreate(this, fileName));
	}
    }
    
    protected void onModify(String fileName) {
	if(listeners != null) {
	    listeners.sub(Listener.class).
		    notifyChange(l -> l.onModify(this, fileName));
	}
    }
    
    protected void onDelete(String fileName) {
	System.out.println(fileNames.remove(fileName));
	if(listeners != null) {
	    listeners.sub(Listener.class).
		    notifyChange(l -> l.onDelete(this, fileName));
	}
    }

    @Override
    public void run() {
	try {
	    WatchKey key = watchService.take();
	    while(key != null && !disposed) {
		for(WatchEvent<?> event : key.pollEvents()) {
		    Path context = (Path) event.context();
		    
		    if	   (event.kind() == ENTRY_CREATE) onCreate(context.toString());
		    else if(event.kind() == ENTRY_MODIFY) onModify(context.toString());
		    else if(event.kind() == ENTRY_DELETE) onDelete(context.toString());
		}
		key.reset();
		key = watchService.take();
	    }
	} catch(InterruptedException e) {
	    e.printStackTrace();
	}
    }

    @Override
    public synchronized void start() {
	started = true;
	super.start();
    }
    
    public void dispose() {
	if(!started) throw new IllegalStateException("PathWatcher not started.");
	//if(disposed) throw new IllegalStateException("PathWatcher alredy disposed.");
	
	disposed = true;
    }

    public boolean isStarted() {
	return started;
    }

    public boolean isDisposed() {
	return disposed;
    }

    @Override
    public void addListener(Observable.Listener listener) {
	listeners().add(listener);
    }

    @Override
    public void addSoftListener(Observable.Listener listener) {
	listeners().addSoft(listener);
    }

    @Override
    public void addWeakListener(Observable.Listener listener) {
	listeners().addWeak(listener);
    }

    @Override
    public void removeListener(Observable.Listener listener) {
	listeners().remove(listener);
    }

    @Override
    public void notifyChange() {
	if(listeners != null) {
	    listeners().notifyChange();
	}
    }

    private Listeners<Observable.Listener> listeners() {
	return listeners != null ? listeners : 
		(listeners = new Listeners<>(this));
    }
    
    public interface Listener extends Observable.Listener {
	public default void onCreate(PathWatcher watcher, String fileName) {}
	public default void onModify(PathWatcher watcher, String fileName) {}
	public default void onDelete(PathWatcher watcher, String fileName) {}
    }
    
}
