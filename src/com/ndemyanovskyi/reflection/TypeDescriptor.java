/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ndemyanovskyi.reflection;

/**
 * A Java field or method type. This class can be used to make it easier to
 * manipulate type and method descriptors.
 *
 * @author Eric Bruneton
 * @author Chris Nokleberg
 */
final class TypeDescriptor {

    /**
     * The sort of array reference types. See {@link #getSort getSort}.
     */
    public static final int ARRAY = 9;

    /**
     * The sort of the <tt>boolean</tt> type. See {@link #getSort getSort}.
     */
    public static final int BOOLEAN = 1;

    /**
     * The <tt>boolean</tt> type.
     */
    public static final TypeDescriptor BOOLEAN_TYPE = new TypeDescriptor(BOOLEAN, null, ('Z' << 24) | (0 << 16) |
	     (5 << 8) | 1, 1);

    /**
     * The sort of the <tt>byte</tt> type. See {@link #getSort getSort}.
     */
    public static final int BYTE = 3;

    /**
     * The <tt>byte</tt> type.
     */
    public static final TypeDescriptor BYTE_TYPE = new TypeDescriptor(BYTE, null, ('B' << 24) | (0 << 16) | (5 << 8) | 1,
	    1);

    /**
     * The sort of the <tt>char</tt> type. See {@link #getSort getSort}.
     */
    public static final int CHAR = 2;

    /**
     * The <tt>char</tt> type.
     */
    public static final TypeDescriptor CHAR_TYPE = new TypeDescriptor(CHAR, null, ('C' << 24) | (0 << 16) | (6 << 8) | 1,
	    1);

    /**
     * The sort of the <tt>double</tt> type. See {@link #getSort getSort}.
     */
    public static final int DOUBLE = 8;

    /**
     * The <tt>double</tt> type.
     */
    public static final TypeDescriptor DOUBLE_TYPE = new TypeDescriptor(DOUBLE, null, ('D' << 24) | (3 << 16) | (3 << 8) |
	     2, 1);

    /**
     * The sort of the <tt>float</tt> type. See {@link #getSort getSort}.
     */
    public static final int FLOAT = 6;

    /**
     * The <tt>float</tt> type.
     */
    public static final TypeDescriptor FLOAT_TYPE = new TypeDescriptor(FLOAT, null, ('F' << 24) | (2 << 16) | (2 << 8) |
	     1, 1);

    /**
     * The sort of the <tt>int</tt> type. See {@link #getSort getSort}.
     */
    public static final int INT = 5;

    /**
     * The <tt>int</tt> type.
     */
    public static final TypeDescriptor INT_TYPE = new TypeDescriptor(INT, null, ('I' << 24) | (0 << 16) | (0 << 8) | 1, 1);

    /**
     * The sort of the <tt>long</tt> type. See {@link #getSort getSort}.
     */
    public static final int LONG = 7;

    /**
     * The <tt>long</tt> type.
     */
    public static final TypeDescriptor LONG_TYPE = new TypeDescriptor(LONG, null, ('J' << 24) | (1 << 16) | (1 << 8) | 2,
	    1);

    /**
     * The sort of method types. See {@link #getSort getSort}.
     */
    public static final int METHOD = 11;

    /**
     * The sort of object reference types. See {@link #getSort getSort}.
     */
    public static final int OBJECT = 10;

    /**
     * The sort of the <tt>short</tt> type. See {@link #getSort getSort}.
     */
    public static final int SHORT = 4;

    /**
     * The <tt>short</tt> type.
     */
    public static final TypeDescriptor SHORT_TYPE = new TypeDescriptor(SHORT, null, ('S' << 24) | (0 << 16) | (7 << 8) |
	     1, 1);

    /**
     * The sort of the <tt>void</tt> type. See {@link #getSort getSort}.
     */
    public static final int VOID = 0;

    /**
     * The <tt>void</tt> type.
     */
    public static final TypeDescriptor VOID_TYPE = new TypeDescriptor(VOID, null, ('V' << 24) | (5 << 16) | (0 << 8) | 0,
	    1);

    /**
     * A buffer containing the internal name of this Java type. This field is
     * only used for reference types.
     */
    private final char[] buf;

    /**
     * The length of the internal name of this Java type.
     */
    private final int len;

    /**
     * The offset of the internal name of this Java type in {@link #buf buf} or,
     * for primitive types, the size, descriptor and getOpcode offsets for this
     * type (byte 0 contains the size, byte 1 the descriptor, byte 2 the offset
     * for IALOAD or IASTORE, byte 3 the offset for all other instructions).
     */
    private final int off;

    /**
     * The sort of this Java type.
     */
    private final int sort;

    /**
     * Constructs a reference type.
     *
     * @param sort the sort of the reference type to be constructed.
     * @param buf a buffer containing the descriptor of the previous type.
     * @param off the offset of this descriptor in the previous buffer.
     * @param len the length of this descriptor.
     */
    private TypeDescriptor(final int sort, final char[] buf, final int off, final int len) {
	this.sort = sort;
	this.buf = buf;
	this.off = off;
	this.len = len;
    }

    /**
     * Returns the Java type corresponding to the given type descriptor. For
     * method descriptors, buf is supposed to contain nothing more than the
     * descriptor itself.
     *
     * @param buf a buffer containing a type descriptor.
     * @param off the offset of this descriptor in the previous buffer.
     * @return the Java type corresponding to the given type descriptor.
     */
    private static TypeDescriptor getType(final char[] buf, final int off) {
	int len;
	switch(buf[off]) {
	    case 'V':
		return VOID_TYPE;
	    case 'Z':
		return BOOLEAN_TYPE;
	    case 'C':
		return CHAR_TYPE;
	    case 'B':
		return BYTE_TYPE;
	    case 'S':
		return SHORT_TYPE;
	    case 'I':
		return INT_TYPE;
	    case 'F':
		return FLOAT_TYPE;
	    case 'J':
		return LONG_TYPE;
	    case 'D':
		return DOUBLE_TYPE;
	    case '[':
		len = 1;
		while(buf[off + len] == '[') {
		    ++len;
		}
		if(buf[off + len] == 'L') {
		    ++len;
		    while(buf[off + len] != ';') {
			++len;
		    }
		}
		return new TypeDescriptor(ARRAY, buf, off, len + 1);
	    case 'L':
		len = 1;
		while(buf[off + len] != ';') {
		    ++len;
		}
		return new TypeDescriptor(OBJECT, buf, off + 1, len - 1);
	    // case '(':
	    default:
		return new TypeDescriptor(METHOD, buf, off, buf.length - off);
	}
    }

    /**
     * Returns the Java types corresponding to the argument types of the given
     * method descriptor.
     *
     * @param methodDescriptor a method descriptor.
     * @return the Java types corresponding to the argument types of the given
     * method descriptor.
     */
    static TypeDescriptor[] getArgumentTypes(final String methodDescriptor) {
	char[] buf = methodDescriptor.toCharArray();
	int off = 1;
	int size = 0;
	while(true) {
	    char car = buf[off++];
	    if(car == ')') {
		break;
	    } else if(car == 'L') {
		while(buf[off++] != ';') {
		}
		++size;
	    } else if(car != '[') {
		++size;
	    }
	}
	TypeDescriptor[] args = new TypeDescriptor[size];
	off = 1;
	size = 0;
	while(buf[off] != ')') {
	    args[size] = getType(buf, off);
	    off += args[size].len + (args[size].sort == OBJECT ? 2 : 0);
	    size += 1;
	}
	return args;
    }

    /**
     * Returns the binary name of the class corresponding to this type. This
     * method must not be used on method types.
     *
     * @return the binary name of the class corresponding to this type.
     */
    public String getClassName() {
	switch(sort) {
	    case VOID:
		return "void";
	    case BOOLEAN:
		return "boolean";
	    case CHAR:
		return "char";
	    case BYTE:
		return "byte";
	    case SHORT:
		return "short";
	    case INT:
		return "int";
	    case FLOAT:
		return "float";
	    case LONG:
		return "long";
	    case DOUBLE:
		return "double";
	    case ARRAY:
		StringBuilder sb = new StringBuilder(getElementType().getClassName());
		for(int i = getDimensions(); i > 0; --i) {
		    sb.append("[]");
		}
		return sb.toString();
	    case OBJECT:
		return new String(buf, off, len).replace('/', '.');
	    default:
		return null;
	}
    }

    /**
     * Returns the Java type corresponding to the return type of the given
     * method descriptor.
     *
     * @param methodDescriptor a method descriptor.
     * @return the Java type corresponding to the return type of the given
     * method descriptor.
     */
    public static TypeDescriptor getReturnType(final String methodDescriptor) {
	char[] buf = methodDescriptor.toCharArray();
	return getType(buf, methodDescriptor.indexOf(')') + 1);
    }

    /**
     * Returns the number of dimensions of this array type. This method should
     * only be used for an array type.
     *
     * @return the number of dimensions of this array type.
     */
    public int getDimensions() {
	int i = 1;
	while(buf[off + i] == '[') {
	    ++i;
	}
	return i;
    }

    /**
     * Returns the type of the elements of this array type. This method should
     * only be used for an array type.
     *
     * @return Returns the type of the elements of this array type.
     */
    public TypeDescriptor getElementType() {
	return getType(buf, off + getDimensions());
    }
}
