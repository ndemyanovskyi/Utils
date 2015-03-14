/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ndemyanovskyi.util;

import java.io.File;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author Назарій
 */
public class FileExtensionFilter extends FileFilter {
    
    private String description;
    private final Set<String> extensions;

    private FileExtensionFilter(Set<String> extensions) {
        this.extensions = Unmodifiable.set(extensions);
    }

    public Set<String> getExtensions() {
        return extensions;
    }
    
    /**
     * @param extensions
     *            a list of allowed extensions, without the dot, e.g.
     *            <code>"xml","html","rss"</code>
     * @return new filter by extensions
     */
    public static FileExtensionFilter of(Collection<String> extensions) {
        return new FileExtensionFilter(new HashSet<>(extensions));
    }
    
    /**
     * @param extensions
     *            a list of allowed extensions, with the dot, e.g.
     *            <code>".xml",".html",".rss"</code>
     * @return new filter by extensions
     */
    public static FileExtensionFilter ofWithDots(Collection<String> extensions) {
        Set<String> set = new HashSet<>();
        for (String extension : extensions) {
            String trimmed = extension.toLowerCase().trim();
            int dot = trimmed.indexOf(".");
            if(dot >= 0) trimmed = trimmed.substring(dot);
            set.add(trimmed);
        }
        return new FileExtensionFilter(set);
    }

    @Override
    public boolean accept(File pathname) {
        if(pathname.isDirectory()) return true;
        
        final Iterator<String> it = extensions.iterator();
        String name = pathname.getName().toLowerCase();
        while (it.hasNext()) {
            if (name.toLowerCase().endsWith("." + it.next())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getDescription() {
        if(description == null) {
            StringBuilder b = new StringBuilder();
            Iterator<String> it = extensions.iterator();
            while(it.hasNext()) {
                b.append('*').append(it.next()).append(it.hasNext() ? ", " : "");
            }
            description = b.toString();
        }
        return description;
    }
    
}
