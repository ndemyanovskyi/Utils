/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ndemyanovskyi.util.number;

import java.util.Collection;
import java.util.Comparator;
import java.util.function.DoublePredicate;
import java.util.function.IntPredicate;
import java.util.function.LongPredicate;
import java.util.function.Predicate;
import java.util.function.Supplier;

public final class Numbers {

    private Numbers() {}

    //<editor-fold defaultstate="collapsed" desc="require">
    public static <N extends Number> N  require(N value, Predicate<? super N> condition) {
        return require(value, condition, "");
    }
    
    public static <N extends Number> N require(N value, Predicate<? super N> condition, String text) {
        if(!condition.test(value)) {
            throw new IllegalArgumentException(text);
        }
        return value;
    }
    
    public static <N extends Number> N require(N value, Predicate<? super N> condition, Supplier<String> text) {
        if(!condition.test(value)) {
            throw new IllegalArgumentException(text.get());
        }
        return value;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="requireNot">
    public static <N extends Number> N requireNot(N value, N invalidValue) {
        return requireNot(value, invalidValue, "value");
    }

    public static <N extends Number> N requireNot(N value, N invalidValue, String text) {
        if(value.equals(invalidValue)) {
            throw new IllegalArgumentException(
                    text + "(" + value + ") can`t be equals to " + invalidValue);
        }
        return value;
    }

    public static <N extends Number> N requireNot(N value, N invalidValue, Supplier<String> text) {
        if(value.equals(invalidValue)) {
            throw new IllegalArgumentException(
                    text.get() + "(" + value + ") can`t = be equals to " + invalidValue);
        }
        return value;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="requireIs">
    public static <N extends Number> N requireIs(N value, N validValue) {
        return requireIs(value, validValue, "value");
    }

    public static <N extends Number> N requireIs(N value, N validValue, String text) {
        if(!value.equals(validValue)) {
            throw new IllegalArgumentException(
                    text + "(" + value + ") must be equals to " + validValue);
        }
        return value;
    }

    public static <N extends Number> N requireIs(N value, N validValue, Supplier<String> text) {
        if(!value.equals(validValue)) {
            throw new IllegalArgumentException(
                    text.get() + "(" + value + ") must be equals to " + validValue);
        }
        return value;
    }
    //</editor-fold>

    public static final class Integers {
	
	public static final Comparator<Integer> COMPARATOR = Integer::compare;

	private Integers() {}

        //<editor-fold defaultstate="collapsed" desc="require">
        public static int require(int value, IntPredicate condition) {
            return require(value, condition, "");
        }
        
        public static int require(int value, IntPredicate condition, String text) {
            if (!condition.test(value)) {
                throw new IllegalArgumentException(text);
            }
            return value;
        }
        
        public static int require(int value, IntPredicate condition, Supplier<String> text) {
            if (!condition.test(value)) {
                throw new IllegalArgumentException(text.get());
            }
            return value;
        }
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="requireNot">
        public static int requireNot(int value, int invalidValue) {
            return requireNot(value, invalidValue, "value");
        }
        
        public static int requireNot(int value, int invalidValue, String text) {
            if (value == invalidValue) {
                throw new IllegalArgumentException(
                        text + "(" + value + ") can`t = be equals to " + invalidValue);
            }
            return value;
        }
        
        public static int requireNot(int value, int invalidValue, Supplier<String> text) {
            if (value == invalidValue) {
                throw new IllegalArgumentException(
                        text.get() + "(" + value + ") can`t be equals to " + invalidValue);
            }
            return value;
        }
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="requireIs">
        public static int requireIs(int value, int validValue) {
            return requireIs(value, validValue, "value");
        }
        
        public static int requireIs(int value, int validValue, String text) {
            if (value != validValue) {
                throw new IllegalArgumentException(
                        text + "(" + value + ") must be equals to " + validValue);
            }
            return value;
        }
        
        public static int requireIs(int value, int validValue, Supplier<String> text) {
            if (value != validValue) {
                throw new IllegalArgumentException(
                        text.get() + "(" + value + ") must be equals to " + validValue);
            }
            return value;
        }
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="requireInBounds">
        public static int requireInBounds(int index, int from, int to) {
            return requireInBounds(index, from, to, "index");
        }
        
        public static int requireInBounds(int index, int from, int to, String name) {
            if (index < from || index > to) {
                throw new IndexOutOfBoundsException(
                        name + " = " + index + "; bounds = [" + from + "; " + to + "]");
            }
            return index;
        }
        
        public static int requireInBounds(int index, int from, int to, Supplier<String> name) {
            if (index < from || index > to) {
                throw new IndexOutOfBoundsException(
                        name.get() + " = " + index + "; bounds = [" + from + "; " + to + "]");
            }
            return index;
        }
        
        public static int requireInBounds(int index, Collection<?> c) {
            return requireInBounds(index, 0, c.size() - 1);
        }
        
        public static int requireInBounds(int index, Collection<?> c, String name) {
            return requireInBounds(index, 0, c.size() - 1, name);
        }
        
        public static int requireInBounds(int index, Collection<?> c, Supplier<String> name) {
            return requireInBounds(index, 0, c.size() - 1, name);
        }
        //</editor-fold>

    }

    public static final class Longs {

	public static final Comparator<Long> COMPARATOR = Long::compare;

	private Longs() {}

        //<editor-fold defaultstate="collapsed" desc="require">
        public static long require(long value, LongPredicate condition) {
            return require(value, condition, "");
        }
        
        public static long require(long value, LongPredicate condition, String text) {
            if (!condition.test(value)) {
                throw new IllegalArgumentException(text);
            }
            return value;
        }
        
        public static long require(long value, LongPredicate condition, Supplier<String> text) {
            if (!condition.test(value)) {
                throw new IllegalArgumentException(text.get());
            }
            return value;
        }
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="requireNot">
        public static long requireNot(long value, long invalidValue) {
            return requireNot(value, invalidValue, "value");
        }
        
        public static long requireNot(long value, long invalidValue, String text) {
            if (value == invalidValue) {
                throw new IllegalArgumentException(
                        text + "(" + value + ") can`t = be equals to " + invalidValue);
            }
            return value;
        }
        
        public static long requireNot(long value, long invalidValue, Supplier<String> text) {
            if (value == invalidValue) {
                throw new IllegalArgumentException(
                        text.get() + "(" + value + ") can`t be equals to " + invalidValue);
            }
            return value;
        }
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="requireIs">
        public static long requireIs(long value, long validValue) {
            return requireIs(value, validValue, "value");
        }
        
        public static long requireIs(long value, long validValue, String text) {
            if (value != validValue) {
                throw new IllegalArgumentException(
                        text + "(" + value + ") must be equals to " + validValue);
            }
            return value;
        }
        
        public static long requireIs(long value, long validValue, Supplier<String> text) {
            if (value != validValue) {
                throw new IllegalArgumentException(
                        text.get() + "(" + value + ") must be equals to " + validValue);
            }
            return value;
        }
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="requireInBounds">
        public static long requireInBounds(long index, long from, long to) {
            return requireInBounds(index, from, to, "index");
        }
        
        public static long requireInBounds(long index, long from, long to, String name) {
            if (index < from || index > to) {
                throw new IndexOutOfBoundsException(
                        name + " = " + index + "; bounds = [" + from + "; " + to + "]");
            }
            return index;
        }
        
        public static long requireInBounds(long index, long from, long to, Supplier<String> name) {
            if (index < from || index > to) {
                throw new IndexOutOfBoundsException(
                        name.get() + " = " + index + "; bounds = [" + from + "; " + to + "]");
            }
            return index;
        }
        //</editor-fold>

    }

    public static final class Floats {

	public static final Comparator<Float> COMPARATOR = Float::compare;

	private Floats() {}

        //<editor-fold defaultstate="collapsed" desc="require">
        public static float require(float value, DoublePredicate condition) {
            return require(value, condition, "");
        }
        
        public static float require(float value, DoublePredicate condition, String text) {
            if (!condition.test(value)) {
                throw new IllegalArgumentException(text);
            }
            return value;
        }
        
        public static float require(float value, DoublePredicate condition, Supplier<String> text) {
            if (!condition.test(value)) {
                throw new IllegalArgumentException(text.get());
            }
            return value;
        }
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="requireNot">
        public static float requireNot(float value, float invalidValue) {
            return requireNot(value, invalidValue, "value");
        }
        
        public static float requireNot(float value, float invalidValue, String text) {
            if (value == invalidValue) {
                throw new IllegalArgumentException(
                        text + "(" + value + ") can`t = be equals to " + invalidValue);
            }
            return value;
        }
        
        public static float requireNot(float value, float invalidValue, Supplier<String> text) {
            if (value == invalidValue) {
                throw new IllegalArgumentException(
                        text.get() + "(" + value + ") can`t be equals to " + invalidValue);
            }
            return value;
        }
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="requireIs">
        public static float requireIs(float value, float validValue) {
            return requireIs(value, validValue, "value");
        }
        
        public static float requireIs(float value, float validValue, String text) {
            if (value != validValue) {
                throw new IllegalArgumentException(
                        text + "(" + value + ") must be equals to " + validValue);
            }
            return value;
        }
        
        public static float requireIs(float value, float validValue, Supplier<String> text) {
            if (value != validValue) {
                throw new IllegalArgumentException(
                        text.get() + "(" + value + ") must be equals to " + validValue);
            }
            return value;
        }
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="requireInBounds">
        public static long requireInBounds(long index, long from, long to) {
            return requireInBounds(index, from, to, "index");
        }
        
        public static long requireInBounds(long index, long from, long to, String name) {
            if (index < from || index > to) {
                throw new IndexOutOfBoundsException(
                        name + " = " + index + "; bounds = [" + from + "; " + to + "]");
            }
            return index;
        }
        
        public static long requireInBounds(long index, long from, long to, Supplier<String> name) {
            if (index < from || index > to) {
                throw new IndexOutOfBoundsException(
                        name.get() + " = " + index + "; bounds = [" + from + "; " + to + "]");
            }
            return index;
        }
        //</editor-fold>

    }

    public static final class Doubles {

	public static final Comparator<Double> COMPARATOR = Double::compare;

	private Doubles() {}

        //<editor-fold defaultstate="collapsed" desc="require">
        public static double require(double value, DoublePredicate condition) {
            return require(value, condition, "");
        }
        
        public static double require(double value, DoublePredicate condition, String text) {
            if (!condition.test(value)) {
                throw new IllegalArgumentException(text);
            }
            return value;
        }
        
        public static double require(double value, DoublePredicate condition, Supplier<String> text) {
            if (!condition.test(value)) {
                throw new IllegalArgumentException(text.get());
            }
            return value;
        }
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="requireNot">
        public static double requireNot(double value, double invalidValue) {
            return requireNot(value, invalidValue, "value");
        }
        
        public static double requireNot(double value, double invalidValue, String text) {
            if (value == invalidValue) {
                throw new IllegalArgumentException(
                        text + "(" + value + ") can`t = be equals to " + invalidValue);
            }
            return value;
        }
        
        public static double requireNot(double value, double invalidValue, Supplier<String> text) {
            if (value == invalidValue) {
                throw new IllegalArgumentException(
                        text.get() + "(" + value + ") can`t be equals to " + invalidValue);
            }
            return value;
        }
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="requireIs">
        public static double requireIs(double value, double validValue) {
            return requireIs(value, validValue, "value");
        }
        
        public static double requireIs(double value, double validValue, String text) {
            if (value != validValue) {
                throw new IllegalArgumentException(
                        text + "(" + value + ") must be equals to " + validValue);
            }
            return value;
        }
        
        public static double requireIs(double value, double validValue, Supplier<String> text) {
            if (value != validValue) {
                throw new IllegalArgumentException(
                        text.get() + "(" + value + ") must be equals to " + validValue);
            }
            return value;
        }
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="requireInBounds">
        public static long requireInBounds(long index, long from, long to) {
            return requireInBounds(index, from, to, "index");
        }
        
        public static long requireInBounds(long index, long from, long to, String name) {
            if (index < from || index > to) {
                throw new IndexOutOfBoundsException(
                        name + " = " + index + "; bounds = [" + from + "; " + to + "]");
            }
            return index;
        }
        
        public static long requireInBounds(long index, long from, long to, Supplier<String> name) {
            if (index < from || index > to) {
                throw new IndexOutOfBoundsException(
                        name.get() + " = " + index + "; bounds = [" + from + "; " + to + "]");
            }
            return index;
        }
        //</editor-fold>

    }

    public static final class Shorts {

	public static final Comparator<Short> COMPARATOR = Short::compare;

	private Shorts() {}

        //<editor-fold defaultstate="collapsed" desc="require">
        public static short require(short value, IntPredicate condition) {
            return require(value, condition, "");
        }
        
        public static short require(short value, IntPredicate condition, String text) {
            if (!condition.test(value)) {
                throw new IllegalArgumentException(text);
            }
            return value;
        }
        
        public static short require(short value, IntPredicate condition, Supplier<String> text) {
            if (!condition.test(value)) {
                throw new IllegalArgumentException(text.get());
            }
            return value;
        }
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="requireNot">
        public static short requireNot(short value, short invalidValue) {
            return requireNot(value, invalidValue, "value");
        }
        
        public static short requireNot(short value, short invalidValue, String text) {
            if (value == invalidValue) {
                throw new IllegalArgumentException(
                        text + "(" + value + ") can`t = be equals to " + invalidValue);
            }
            return value;
        }
        
        public static short requireNot(short value, short invalidValue, Supplier<String> text) {
            if (value == invalidValue) {
                throw new IllegalArgumentException(
                        text.get() + "(" + value + ") can`t be equals to " + invalidValue);
            }
            return value;
        }
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="requireIs">
        public static short requireIs(short value, short validValue) {
            return requireIs(value, validValue, "value");
        }
        
        public static short requireIs(short value, short validValue, String text) {
            if (value != validValue) {
                throw new IllegalArgumentException(
                        text + "(" + value + ") must be equals to " + validValue);
            }
            return value;
        }
        
        public static short requireIs(short value, short validValue, Supplier<String> text) {
            if (value != validValue) {
                throw new IllegalArgumentException(
                        text.get() + "(" + value + ") must be equals to " + validValue);
            }
            return value;
        }
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="requireInBounds">
        public static short requireInBounds(short index, short from, short to) {
            return requireInBounds(index, from, to, "index");
        }
        
        public static short requireInBounds(short index, short from, short to, String name) {
            if (index < from || index > to) {
                throw new IndexOutOfBoundsException(
                        name + " = " + index + "; bounds = [" + from + "; " + to + "]");
            }
            return index;
        }
        
        public static short requireInBounds(short index, short from, short to, Supplier<String> name) {
            if (index < from || index > to) {
                throw new IndexOutOfBoundsException(
                        name.get() + " = " + index + "; bounds = [" + from + "; " + to + "]");
            }
            return index;
        }
        //</editor-fold>

    }

    public static final class Bytes {

	public static final Comparator<Byte> COMPARATOR = Byte::compare;

	private Bytes() {}

        //<editor-fold defaultstate="collapsed" desc="require">
        public static byte require(byte value, IntPredicate condition) {
            return require(value, condition, "");
        }
        
        public static byte require(byte value, IntPredicate condition, String text) {
            if (!condition.test(value)) {
                throw new IllegalArgumentException(text);
            }
            return value;
        }
        
        public static byte require(byte value, IntPredicate condition, Supplier<String> text) {
            if (!condition.test(value)) {
                throw new IllegalArgumentException(text.get());
            }
            return value;
        }
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="requireNot">
        public static byte requireNot(byte value, byte invalidValue) {
            return requireNot(value, invalidValue, "value");
        }
        
        public static byte requireNot(byte value, byte invalidValue, String text) {
            if (value == invalidValue) {
                throw new IllegalArgumentException(
                        text + "(" + value + ") can`t = be equals to " + invalidValue);
            }
            return value;
        }
        
        public static byte requireNot(byte value, byte invalidValue, Supplier<String> text) {
            if (value == invalidValue) {
                throw new IllegalArgumentException(
                        text.get() + "(" + value + ") can`t be equals to " + invalidValue);
            }
            return value;
        }
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="requireIs">
        public static byte requireIs(byte value, byte validValue) {
            return requireIs(value, validValue, "value");
        }
        
        public static byte requireIs(byte value, byte validValue, String text) {
            if (value != validValue) {
                throw new IllegalArgumentException(
                        text + "(" + value + ") must be equals to " + validValue);
            }
            return value;
        }
        
        public static byte requireIs(byte value, byte validValue, Supplier<String> text) {
            if (value != validValue) {
                throw new IllegalArgumentException(
                        text.get() + "(" + value + ") must be equals to " + validValue);
            }
            return value;
        }
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="requireInBounds">
        public static byte requireInBounds(byte index, byte from, byte to) {
            return requireInBounds(index, from, to, "index");
        }
        
        public static byte requireInBounds(byte index, byte from, byte to, String name) {
            if (index < from || index > to) {
                throw new IndexOutOfBoundsException(
                        name + " = " + index + "; bounds = [" + from + "; " + to + "]");
            }
            return index;
        }
        
        public static byte requireInBounds(byte index, byte from, byte to, Supplier<String> name) {
            if (index < from || index > to) {
                throw new IndexOutOfBoundsException(
                        name.get() + " = " + index + "; bounds = [" + from + "; " + to + "]");
            }
            return index;
        }
        //</editor-fold>

    }

}
