/*
 * Copyright (c) 1994, 2013, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

package java.lang;

import java.lang.annotation.Native;
import java.math.*;


/**
 * The {@code Long} class wraps a value of the primitive type {@code
 * long} in an object. An object of type {@code Long} contains a
 * single field whose type is {@code long}.
 *
 * <p> In addition, this class provides several methods for converting
 * a {@code long} to a {@code String} and a {@code String} to a {@code
 * long}, as well as other constants and methods useful when dealing
 * with a {@code long}.
 *
 * <p>Implementation note: The implementations of the "bit twiddling"
 * methods (such as {@link #highestOneBit(long) highestOneBit} and
 * {@link #numberOfTrailingZeros(long) numberOfTrailingZeros}) are
 * based on material from Henry S. Warren, Jr.'s <i>Hacker's
 * Delight</i>, (Addison Wesley, 2002).
 *
 * @author  Lee Boynton
 * @author  Arthur van Hoff
 * @author  Josh Bloch
 * @author  Joseph D. Darcy
 * @since   JDK1.0
 */

/**
 * Long:64位
 */
public final class Long extends Number implements Comparable<Long> {
    /**
     * A constant holding the minimum value a {@code long} can
     * have, -2<sup>63</sup>.
     */
    // 最小值： -9223372036854775808（-2<sup>63</sup>.）
    @Native public static final long MIN_VALUE = 0x8000000000000000L;

    /**
     * A constant holding the maximum value a {@code long} can
     * have, 2<sup>63</sup>-1.
     */
    // 最大值：9223372036854775807（2<sup>63</sup>-1.）
    @Native public static final long MAX_VALUE = 0x7fffffffffffffffL;

    /**
     * The {@code Class} instance representing the primitive type
     * {@code long}.
     *
     * @since   JDK1.1
     */
    @SuppressWarnings("unchecked")
    public static final Class<Long>     TYPE = (Class<Long>) Class.getPrimitiveClass("long");

    /**
     * Returns a string representation of the first argument in the
     * radix specified by the second argument.
     *
     * <p>If the radix is smaller than {@code Character.MIN_RADIX}
     * or larger than {@code Character.MAX_RADIX}, then the radix
     * {@code 10} is used instead.
     *
     * <p>If the first argument is negative, the first element of the
     * result is the ASCII minus sign {@code '-'}
     * ({@code '\u005Cu002d'}). If the first argument is not
     * negative, no sign character appears in the result.
     *
     * <p>The remaining characters of the result represent the magnitude
     * of the first argument. If the magnitude is zero, it is
     * represented by a single zero character {@code '0'}
     * ({@code '\u005Cu0030'}); otherwise, the first character of
     * the representation of the magnitude will not be the zero
     * character.  The following ASCII characters are used as digits:
     *
     * <blockquote>
     *   {@code 0123456789abcdefghijklmnopqrstuvwxyz}
     * </blockquote>
     *
     * These are {@code '\u005Cu0030'} through
     * {@code '\u005Cu0039'} and {@code '\u005Cu0061'} through
     * {@code '\u005Cu007a'}. If {@code radix} is
     * <var>N</var>, then the first <var>N</var> of these characters
     * are used as radix-<var>N</var> digits in the order shown. Thus,
     * the digits for hexadecimal (radix 16) are
     * {@code 0123456789abcdef}. If uppercase letters are
     * desired, the {@link java.lang.String#toUpperCase()} method may
     * be called on the result:
     *
     * <blockquote>
     *  {@code Long.toString(n, 16).toUpperCase()}
     * </blockquote>
     *
     * @param   i       a {@code long} to be converted to a string.
     * @param   radix   the radix to use in the string representation.
     * @return  a string representation of the argument in the specified radix.
     * @see     java.lang.Character#MAX_RADIX
     * @see     java.lang.Character#MIN_RADIX
     */
    /**
     * 有符号转化进制
     *
     * 进制范围在2-36之间，否则为10进制
     *
     * 1)先转化为负数
     * 2）求余数
     * 3）求商
     * 4）是否需要添加负号
     * 5）截取字符串
     */
    public static String toString(long i, int radix) {
        if (radix < Character.MIN_RADIX || radix > Character.MAX_RADIX)
            radix = 10;
        if (radix == 10)
            return toString(i);
        char[] buf = new char[65];
        int charPos = 64;
        boolean negative = (i < 0);    // 判断是否为负数

        // 非负数的时候 i >= 0
        if (!negative) {
            i = -i;
        }

        while (i <= -radix) {
            buf[charPos--] = Integer.digits[(int)(-(i % radix))];
            i = i / radix;
        }
        buf[charPos] = Integer.digits[(int)(-i)];

        // 如果是负数的时候
        if (negative) {
            buf[--charPos] = '-';
        }

        return new String(buf, charPos, (65 - charPos));
    }

    /**
     * Returns a string representation of the first argument as an
     * unsigned integer value in the radix specified by the second
     * argument.
     *
     * <p>If the radix is smaller than {@code Character.MIN_RADIX}
     * or larger than {@code Character.MAX_RADIX}, then the radix
     * {@code 10} is used instead.
     *
     * <p>Note that since the first argument is treated as an unsigned
     * value, no leading sign character is printed.
     *
     * <p>If the magnitude is zero, it is represented by a single zero
     * character {@code '0'} ({@code '\u005Cu0030'}); otherwise,
     * the first character of the representation of the magnitude will
     * not be the zero character.
     *
     * <p>The behavior of radixes and the characters used as digits
     * are the same as {@link #toString(long, int) toString}.
     *
     * @param   i       an integer to be converted to an unsigned string.
     * @param   radix   the radix to use in the string representation.
     * @return  an unsigned string representation of the argument in the specified radix.
     * @see     #toString(long, int)
     * @since 1.8
     */
    // 返回无符号的基数字符串
    public static String toUnsignedString(long i, int radix) {
        if (i >= 0)
            return toString(i, radix);
        else {
            switch (radix) {
            case 2:
                return toBinaryString(i);

            case 4:
                return toUnsignedString0(i, 2);

            case 8:
                return toOctalString(i);

            case 10:
                /*
                 * We can get the effect of an unsigned division by 10
                 * on a long value by first shifting right, yielding a
                 * positive value, and then dividing by 5.  This
                 * allows the last digit and preceding digits to be
                 * isolated more quickly than by an initial conversion
                 * to BigInteger.
                 */
                /**
                 * 我们可以通过首先向右移动，产生正值，然后除以5，
                 * 得到无符号除法对long值的影响。这使得最后一位和前面的数字比初始转换为更快。BigInteger的。
                 */
                long quot = (i >>> 1) / 5;
                long rem = i - quot * 10;
                return toString(quot) + rem;

            case 16:
                return toHexString(i);

            case 32:
                return toUnsignedString0(i, 5);

            default:
                // ??
                return toUnsignedBigInteger(i).toString(radix);
            }
        }
    }

    /**
     * Return a BigInteger equal to the unsigned value of the
     * argument.
     */
    private static BigInteger toUnsignedBigInteger(long i) {
        if (i >= 0L)
            return BigInteger.valueOf(i);
        else {
            int upper = (int) (i >>> 32);
            int lower = (int) i;

            // return (upper << 32) + lower
            return (BigInteger.valueOf(Integer.toUnsignedLong(upper))).shiftLeft(32).
                add(BigInteger.valueOf(Integer.toUnsignedLong(lower)));
        }
    }

    /**
     * Returns a string representation of the {@code long}
     * argument as an unsigned integer in base&nbsp;16.
     *
     * <p>The unsigned {@code long} value is the argument plus
     * 2<sup>64</sup> if the argument is negative; otherwise, it is
     * equal to the argument.  This value is converted to a string of
     * ASCII digits in hexadecimal (base&nbsp;16) with no extra
     * leading {@code 0}s.
     *
     * <p>The value of the argument can be recovered from the returned
     * string {@code s} by calling {@link
     * Long#parseUnsignedLong(String, int) Long.parseUnsignedLong(s,
     * 16)}.
     *
     * <p>If the unsigned magnitude is zero, it is represented by a
     * single zero character {@code '0'} ({@code '\u005Cu0030'});
     * otherwise, the first character of the representation of the
     * unsigned magnitude will not be the zero character. The
     * following characters are used as hexadecimal digits:
     *
     * <blockquote>
     *  {@code 0123456789abcdef}
     * </blockquote>
     *
     * These are the characters {@code '\u005Cu0030'} through
     * {@code '\u005Cu0039'} and  {@code '\u005Cu0061'} through
     * {@code '\u005Cu0066'}.  If uppercase letters are desired,
     * the {@link java.lang.String#toUpperCase()} method may be called
     * on the result:
     *
     * <blockquote>
     *  {@code Long.toHexString(n).toUpperCase()}
     * </blockquote>
     *
     * @param   i   a {@code long} to be converted to a string.
     * @return  the string representation of the unsigned {@code long}
     *          value represented by the argument in hexadecimal
     *          (base&nbsp;16).
     * @see #parseUnsignedLong(String, int)
     * @see #toUnsignedString(long, int)
     * @since   JDK 1.0.2
     */
    // 16进制字符串
    public static String toHexString(long i) {
        return toUnsignedString0(i, 4);
    }

    /**
     * Returns a string representation of the {@code long}
     * argument as an unsigned integer in base&nbsp;8.
     *
     * <p>The unsigned {@code long} value is the argument plus
     * 2<sup>64</sup> if the argument is negative; otherwise, it is
     * equal to the argument.  This value is converted to a string of
     * ASCII digits in octal (base&nbsp;8) with no extra leading
     * {@code 0}s.
     *
     * <p>The value of the argument can be recovered from the returned
     * string {@code s} by calling {@link
     * Long#parseUnsignedLong(String, int) Long.parseUnsignedLong(s,
     * 8)}.
     *
     * <p>If the unsigned magnitude is zero, it is represented by a
     * single zero character {@code '0'} ({@code '\u005Cu0030'});
     * otherwise, the first character of the representation of the
     * unsigned magnitude will not be the zero character. The
     * following characters are used as octal digits:
     *
     * <blockquote>
     *  {@code 01234567}
     * </blockquote>
     *
     * These are the characters {@code '\u005Cu0030'} through
     * {@code '\u005Cu0037'}.
     *
     * @param   i   a {@code long} to be converted to a string.
     * @return  the string representation of the unsigned {@code long}
     *          value represented by the argument in octal (base&nbsp;8).
     * @see #parseUnsignedLong(String, int)
     * @see #toUnsignedString(long, int)
     * @since   JDK 1.0.2
     */
    // 8进制字符串
    public static String toOctalString(long i) {
        return toUnsignedString0(i, 3);
    }

    /**
     * Returns a string representation of the {@code long}
     * argument as an unsigned integer in base&nbsp;2.
     *
     * <p>The unsigned {@code long} value is the argument plus
     * 2<sup>64</sup> if the argument is negative; otherwise, it is
     * equal to the argument.  This value is converted to a string of
     * ASCII digits in binary (base&nbsp;2) with no extra leading
     * {@code 0}s.
     *
     * <p>The value of the argument can be recovered from the returned
     * string {@code s} by calling {@link
     * Long#parseUnsignedLong(String, int) Long.parseUnsignedLong(s,
     * 2)}.
     *
     * <p>If the unsigned magnitude is zero, it is represented by a
     * single zero character {@code '0'} ({@code '\u005Cu0030'});
     * otherwise, the first character of the representation of the
     * unsigned magnitude will not be the zero character. The
     * characters {@code '0'} ({@code '\u005Cu0030'}) and {@code
     * '1'} ({@code '\u005Cu0031'}) are used as binary digits.
     *
     * @param   i   a {@code long} to be converted to a string.
     * @return  the string representation of the unsigned {@code long}
     *          value represented by the argument in binary (base&nbsp;2).
     * @see #parseUnsignedLong(String, int)
     * @see #toUnsignedString(long, int)
     * @since   JDK 1.0.2
     */
    // 2进制字符串
    public static String toBinaryString(long i) {
        return toUnsignedString0(i, 1);
    }

    /**
     * Format a long (treated as unsigned) into a String.
     * @param val the value to format
     * @param shift the log2 of the base to format in (4 for hex, 3 for octal, 1 for binary)
     */
    /**
     * 将long（视为无符号）格式化为String。
     * @param val
     * @param shift 要格式化的基数的log2（4表示十六进制，3表示八进制，1表示二进制）
     * @return
     */
    static String toUnsignedString0(long val, int shift) {
        // assert shift > 0 && shift <=5 : "Illegal shift value";
        int mag = Long.SIZE - Long.numberOfLeadingZeros(val);
        int chars = Math.max(((mag + (shift - 1)) / shift), 1);
        char[] buf = new char[chars];

        formatUnsignedLong(val, shift, buf, 0, chars);
        return new String(buf, true);
    }

    /**
     * Format a long (treated as unsigned) into a character buffer.
     * @param val the unsigned long to format
     * @param shift the log2 of the base to format in (4 for hex, 3 for octal, 1 for binary)
     * @param buf the character buffer to write to
     * @param offset the offset in the destination buffer to start at
     * @param len the number of characters to write
     * @return the lowest character location used
     */
     static int formatUnsignedLong(long val, int shift, char[] buf, int offset, int len) {
        int charPos = len;
        int radix = 1 << shift;
        int mask = radix - 1;
        do {
            // ？？高深
            buf[offset + --charPos] = Integer.digits[((int) val) & mask];

            // 基数和每次移动的位数有关系
            val >>>= shift;
        } while (val != 0 && charPos > 0);

        return charPos;
    }

    /**
     * Returns a {@code String} object representing the specified
     * {@code long}.  The argument is converted to signed decimal
     * representation and returned as a string, exactly as if the
     * argument and the radix 10 were given as arguments to the {@link
     * #toString(long, int)} method.
     *
     * @param   i   a {@code long} to be converted.
     * @return  a string representation of the argument in base&nbsp;10.
     */
    /**
     * 将long类型的值返回十进制的字符串
     */
    public static String toString(long i) {
        if (i == Long.MIN_VALUE)
            return "-9223372036854775808";
        int size = (i < 0) ? stringSize(-i) + 1 : stringSize(i);
        char[] buf = new char[size];
        getChars(i, size, buf);
        return new String(buf, true);
    }

    /**
     * Returns a string representation of the argument as an unsigned
     * decimal value.
     *
     * The argument is converted to unsigned decimal representation
     * and returned as a string exactly as if the argument and radix
     * 10 were given as arguments to the {@link #toUnsignedString(long,
     * int)} method.
     *
     * @param   i  an integer to be converted to an unsigned string.
     * @return  an unsigned string representation of the argument.
     * @see     #toUnsignedString(long, int)
     * @since 1.8
     */
    // 10进制无符号字符串
    public static String toUnsignedString(long i) {
        return toUnsignedString(i, 10);
    }

    /**
     * Places characters representing the integer i into the
     * character array buf. The characters are placed into
     * the buffer backwards starting with the least significant
     * digit at the specified index (exclusive), and working
     * backwards from there.
     *
     * Will fail if i == Long.MIN_VALUE
     */
    static void getChars(long i, int index, char[] buf) {
        long q;
        int r;
        int charPos = index;
        char sign = 0;

        if (i < 0) {
            sign = '-';
            i = -i;
        }

        // Get 2 digits/iteration using longs until quotient fits into an int
        while (i > Integer.MAX_VALUE) {
            q = i / 100;
            // really: r = i - (q * 100);
            r = (int)(i - ((q << 6) + (q << 5) + (q << 2)));  // 求出原来数据的个位和十位数据
            i = q;
            buf[--charPos] = Integer.DigitOnes[r];
            buf[--charPos] = Integer.DigitTens[r];
        }

        // Get 2 digits/iteration using ints
        int q2;
        int i2 = (int)i;
        // Character MAX_VALUE: 65535
        while (i2 >= 65536) {
            q2 = i2 / 100;
            // really: r = i2 - (q * 100);
            r = i2 - ((q2 << 6) + (q2 << 5) + (q2 << 2));
            i2 = q2;
            buf[--charPos] = Integer.DigitOnes[r];
            buf[--charPos] = Integer.DigitTens[r];
        }

        // Fall thru to fast mode for smaller numbers
        // assert(i2 <= 65536, i2);
        for (;;) {
            /**
             * i2 <= 65536的时候，下面就相当于除以10
             *
             * Math.pow( 2, 19 ) => 524288
             *
             * i/10;
             *
             * q2 = (i2 * 52429) >>> (16+3); 52429/524288 = 0.10000038146972656, 524288 = 1 << 19，
             * 换句话说q2 = (i2 * 52429) >>> (16+3);就是q2 = i2/10为了避免效率低下的除法
             *
             * i取最后两位;
             *
             * r = (int)(i - ((q << 6) + (q << 5) + (q << 2)));很巧妙的使用高效的位移运算完成了r = i - (q * 100),
             * 具体实现方式：100 = 64 + 32 + 4，通过位移q再相加，完美实现q * 100。
             */
            q2 = (i2 * 52429) >>> (16+3);
            r = i2 - ((q2 << 3) + (q2 << 1));  // r = i2-(q2*10) ...
            buf[--charPos] = Integer.digits[r];
            i2 = q2;
            if (i2 == 0) break;
        }
        if (sign != 0) {
            buf[--charPos] = sign;
        }
    }

    // Requires positive x

    /**
     * 为什么是19呢？
     * 因为Long类型的值正数的时候，最大长度为19。
     * System.out.println("9223372036854775807".length());
     */
    static int stringSize(long x) {
        long p = 10;

        /**
         * 求位数，每次乘以10，直到大于原来的数
         */
        for (int i=1; i<19; i++) {
            if (x < p)
                return i;
            p = 10*p;
        }

        // 超过最大值得时候，默认位数为19
        return 19;
    }

    /**
     * Parses the string argument as a signed {@code long} in the
     * radix specified by the second argument. The characters in the
     * string must all be digits of the specified radix (as determined
     * by whether {@link java.lang.Character#digit(char, int)} returns
     * a nonnegative value), except that the first character may be an
     * ASCII minus sign {@code '-'} ({@code '\u005Cu002D'}) to
     * indicate a negative value or an ASCII plus sign {@code '+'}
     * ({@code '\u005Cu002B'}) to indicate a positive value. The
     * resulting {@code long} value is returned.
     *
     * <p>Note that neither the character {@code L}
     * ({@code '\u005Cu004C'}) nor {@code l}
     * ({@code '\u005Cu006C'}) is permitted to appear at the end
     * of the string as a type indicator, as would be permitted in
     * Java programming language source code - except that either
     * {@code L} or {@code l} may appear as a digit for a
     * radix greater than or equal to 22.
     *
     * <p>An exception of type {@code NumberFormatException} is
     * thrown if any of the following situations occurs:
     * <ul>
     *
     * <li>The first argument is {@code null} or is a string of
     * length zero.
     *
     * <li>The {@code radix} is either smaller than {@link
     * java.lang.Character#MIN_RADIX} or larger than {@link
     * java.lang.Character#MAX_RADIX}.
     *
     * <li>Any character of the string is not a digit of the specified
     * radix, except that the first character may be a minus sign
     * {@code '-'} ({@code '\u005Cu002d'}) or plus sign {@code
     * '+'} ({@code '\u005Cu002B'}) provided that the string is
     * longer than length 1.
     *
     * <li>The value represented by the string is not a value of type
     *      {@code long}.
     * </ul>
     *
     * <p>Examples:
     * <blockquote><pre>
     * parseLong("0", 10) returns 0L
     * parseLong("473", 10) returns 473L
     * parseLong("+42", 10) returns 42L
     * parseLong("-0", 10) returns 0L
     * parseLong("-FF", 16) returns -255L
     * parseLong("1100110", 2) returns 102L
     * parseLong("99", 8) throws a NumberFormatException
     * parseLong("Hazelnut", 10) throws a NumberFormatException
     * parseLong("Hazelnut", 36) returns 1356099454469L
     * </pre></blockquote>
     *
     * @param      s       the {@code String} containing the
     *                     {@code long} representation to be parsed.
     * @param      radix   the radix to be used while parsing {@code s}.
     * @return     the {@code long} represented by the string argument in
     *             the specified radix.
     * @throws     NumberFormatException  if the string does not contain a
     *             parsable {@code long}.
     */
    /**
     * 字符串结尾不能有L或者l，
     */
    public static long parseLong(String s, int radix)
              throws NumberFormatException
    {
        if (s == null) {
            throw new NumberFormatException("null");
        }

        if (radix < Character.MIN_RADIX) {
            throw new NumberFormatException("radix " + radix +
                                            " less than Character.MIN_RADIX");
        }
        if (radix > Character.MAX_RADIX) {
            throw new NumberFormatException("radix " + radix +
                                            " greater than Character.MAX_RADIX");
        }

        long result = 0;
        boolean negative = false;   // 是否负数标记
        int i = 0, len = s.length();
        long limit = -Long.MAX_VALUE;
        long multmin;
        int digit;

        if (len > 0) {
            char firstChar = s.charAt(0);
            if (firstChar < '0') { // Possible leading "+" or "-"
                if (firstChar == '-') {
                    negative = true;
                    limit = Long.MIN_VALUE;
                } else if (firstChar != '+')
                    throw NumberFormatException.forInputString(s);

                if (len == 1) // Cannot have lone "+" or "-"
                    throw NumberFormatException.forInputString(s);
                i++;
            }
            multmin = limit / radix;
            while (i < len) {
                // Accumulating negatively avoids surprises near MAX_VALUE
                // ?? 暂不知下面的内容意义
                digit = Character.digit(s.charAt(i++),radix);
                if (digit < 0) {
                    throw NumberFormatException.forInputString(s);
                }
                if (result < multmin) {
                    throw NumberFormatException.forInputString(s);
                }
                result *= radix;
                if (result < limit + digit) {
                    throw NumberFormatException.forInputString(s);
                }
                result -= digit;
            }
        } else {
            throw NumberFormatException.forInputString(s);
        }
        return negative ? result : -result;
    }

    /**
     * Parses the string argument as a signed decimal {@code long}.
     * The characters in the string must all be decimal digits, except
     * that the first character may be an ASCII minus sign {@code '-'}
     * ({@code \u005Cu002D'}) to indicate a negative value or an
     * ASCII plus sign {@code '+'} ({@code '\u005Cu002B'}) to
     * indicate a positive value. The resulting {@code long} value is
     * returned, exactly as if the argument and the radix {@code 10}
     * were given as arguments to the {@link
     * #parseLong(java.lang.String, int)} method.
     *
     * <p>Note that neither the character {@code L}
     * ({@code '\u005Cu004C'}) nor {@code l}
     * ({@code '\u005Cu006C'}) is permitted to appear at the end
     * of the string as a type indicator, as would be permitted in
     * Java programming language source code.
     *
     * @param      s   a {@code String} containing the {@code long}
     *             representation to be parsed
     * @return     the {@code long} represented by the argument in
     *             decimal.
     * @throws     NumberFormatException  if the string does not contain a
     *             parsable {@code long}.
     */
    /**
     * 10进制
     */
    public static long parseLong(String s) throws NumberFormatException {
        return parseLong(s, 10);
    }

    /**
     * Parses the string argument as an unsigned {@code long} in the
     * radix specified by the second argument.  An unsigned integer
     * maps the values usually associated with negative numbers to
     * positive numbers larger than {@code MAX_VALUE}.
     *
     * The characters in the string must all be digits of the
     * specified radix (as determined by whether {@link
     * java.lang.Character#digit(char, int)} returns a nonnegative
     * value), except that the first character may be an ASCII plus
     * sign {@code '+'} ({@code '\u005Cu002B'}). The resulting
     * integer value is returned.
     *
     * <p>An exception of type {@code NumberFormatException} is
     * thrown if any of the following situations occurs:
     * <ul>
     * <li>The first argument is {@code null} or is a string of
     * length zero.
     *
     * <li>The radix is either smaller than
     * {@link java.lang.Character#MIN_RADIX} or
     * larger than {@link java.lang.Character#MAX_RADIX}.
     *
     * <li>Any character of the string is not a digit of the specified
     * radix, except that the first character may be a plus sign
     * {@code '+'} ({@code '\u005Cu002B'}) provided that the
     * string is longer than length 1.
     *
     * <li>The value represented by the string is larger than the
     * largest unsigned {@code long}, 2<sup>64</sup>-1.
     *
     * </ul>
     *
     *
     * @param      s   the {@code String} containing the unsigned integer
     *                  representation to be parsed
     * @param      radix   the radix to be used while parsing {@code s}.
     * @return     the unsigned {@code long} represented by the string
     *             argument in the specified radix.
     * @throws     NumberFormatException if the {@code String}
     *             does not contain a parsable {@code long}.
     * @since 1.8
     */
    /**
     * 字符串第一个字符不能为-，否则会报错误。
     */
    public static long parseUnsignedLong(String s, int radix)
                throws NumberFormatException {
        if (s == null)  {
            throw new NumberFormatException("null");
        }

        int len = s.length();
        if (len > 0) {
            char firstChar = s.charAt(0);
            if (firstChar == '-') {
                throw new
                    NumberFormatException(String.format("Illegal leading minus sign " +
                                                       "on unsigned string %s.", s));
            } else {
                // ??
                if (len <= 12 || // Long.MAX_VALUE in Character.MAX_RADIX is 13 digits
                    (radix == 10 && len <= 18) ) { // Long.MAX_VALUE in base 10 is 19 digits
                    return parseLong(s, radix);
                }

                // No need for range checks on len due to testing above.
                long first = parseLong(s.substring(0, len - 1), radix);
                int second = Character.digit(s.charAt(len - 1), radix);
                if (second < 0) {
                    throw new NumberFormatException("Bad digit at end of " + s);
                }
                long result = first * radix + second;
                if (compareUnsigned(result, first) < 0) {
                    /*
                     * The maximum unsigned value, (2^64)-1, takes at
                     * most one more digit to represent than the
                     * maximum signed value, (2^63)-1.  Therefore,
                     * parsing (len - 1) digits will be appropriately
                     * in-range of the signed parsing.  In other
                     * words, if parsing (len -1) digits overflows
                     * signed parsing, parsing len digits will
                     * certainly overflow unsigned parsing.
                     *
                     * The compareUnsigned check above catches
                     * situations where an unsigned overflow occurs
                     * incorporating the contribution of the final
                     * digit.
                     */
                    throw new NumberFormatException(String.format("String value %s exceeds " +
                                                                  "range of unsigned long.", s));
                }
                return result;
            }
        } else {
            throw NumberFormatException.forInputString(s);
        }
    }

    /**
     * Parses the string argument as an unsigned decimal {@code long}. The
     * characters in the string must all be decimal digits, except
     * that the first character may be an an ASCII plus sign {@code
     * '+'} ({@code '\u005Cu002B'}). The resulting integer value
     * is returned, exactly as if the argument and the radix 10 were
     * given as arguments to the {@link
     * #parseUnsignedLong(java.lang.String, int)} method.
     *
     * @param s   a {@code String} containing the unsigned {@code long}
     *            representation to be parsed
     * @return    the unsigned {@code long} value represented by the decimal string argument
     * @throws    NumberFormatException  if the string does not contain a
     *            parsable unsigned integer.
     * @since 1.8
     */
    // 无符号
    public static long parseUnsignedLong(String s) throws NumberFormatException {
        return parseUnsignedLong(s, 10);
    }

    /**
     * Returns a {@code Long} object holding the value
     * extracted from the specified {@code String} when parsed
     * with the radix given by the second argument.  The first
     * argument is interpreted as representing a signed
     * {@code long} in the radix specified by the second
     * argument, exactly as if the arguments were given to the {@link
     * #parseLong(java.lang.String, int)} method. The result is a
     * {@code Long} object that represents the {@code long}
     * value specified by the string.
     *
     * <p>In other words, this method returns a {@code Long} object equal
     * to the value of:
     *
     * <blockquote>
     *  {@code new Long(Long.parseLong(s, radix))}
     * </blockquote>
     *
     * @param      s       the string to be parsed
     * @param      radix   the radix to be used in interpreting {@code s}
     * @return     a {@code Long} object holding the value
     *             represented by the string argument in the specified
     *             radix.
     * @throws     NumberFormatException  If the {@code String} does not
     *             contain a parsable {@code long}.
     */
    public static Long valueOf(String s, int radix) throws NumberFormatException {
        // 这里精妙之处，Long.valueOf可以节省内存，使用缓存数据
        return Long.valueOf(parseLong(s, radix));
    }

    /**
     * Returns a {@code Long} object holding the value
     * of the specified {@code String}. The argument is
     * interpreted as representing a signed decimal {@code long},
     * exactly as if the argument were given to the {@link
     * #parseLong(java.lang.String)} method. The result is a
     * {@code Long} object that represents the integer value
     * specified by the string.
     *
     * <p>In other words, this method returns a {@code Long} object
     * equal to the value of:
     *
     * <blockquote>
     *  {@code new Long(Long.parseLong(s))}
     * </blockquote>
     *
     * @param      s   the string to be parsed.
     * @return     a {@code Long} object holding the value
     *             represented by the string argument.
     * @throws     NumberFormatException  If the string cannot be parsed
     *             as a {@code long}.
     */
    // 十进制
    public static Long valueOf(String s) throws NumberFormatException
    {
        return Long.valueOf(parseLong(s, 10));
    }

    // Long/Integer缓存区，在-128到127，取得值都是一个，但超过范围了是新生成的
    private static class LongCache {
        private LongCache(){}

        static final Long cache[] = new Long[-(-128) + 127 + 1];

        static {
            for(int i = 0; i < cache.length; i++)
                cache[i] = new Long(i - 128);
        }
    }

    /**
     * Returns a {@code Long} instance representing the specified
     * {@code long} value.
     * If a new {@code Long} instance is not required, this method
     * should generally be used in preference to the constructor
     * {@link #Long(long)}, as this method is likely to yield
     * significantly better space and time performance by caching
     * frequently requested values.
     *
     * Note that unlike the {@linkplain Integer#valueOf(int)
     * corresponding method} in the {@code Integer} class, this method
     * is <em>not</em> required to cache values within a particular
     * range.
     *
     * @param  l a long value.
     * @return a {@code Long} instance representing {@code l}.
     * @since  1.5
     */
    /**
     * 如果不需要新的{@code Long}实例，通常应优先使用此方法而不是构造函数 {@link #Long（long）}，
     * 因为此方法可能会产生明显更好的空间和时间性能通过缓存经常请求的值。
     */
    public static Long valueOf(long l) {
        final int offset = 128;
        if (l >= -128 && l <= 127) { // will cache
            return LongCache.cache[(int)l + offset];
        }
        return new Long(l);
    }

    /**
     * Decodes a {@code String} into a {@code Long}.
     * Accepts decimal, hexadecimal, and octal numbers given by the
     * following grammar:
     *
     * <blockquote>
     * <dl>
     * <dt><i>DecodableString:</i>
     * <dd><i>Sign<sub>opt</sub> DecimalNumeral</i>
     * <dd><i>Sign<sub>opt</sub></i> {@code 0x} <i>HexDigits</i>
     * <dd><i>Sign<sub>opt</sub></i> {@code 0X} <i>HexDigits</i>
     * <dd><i>Sign<sub>opt</sub></i> {@code #} <i>HexDigits</i>
     * <dd><i>Sign<sub>opt</sub></i> {@code 0} <i>OctalDigits</i>
     *
     * <dt><i>Sign:</i>
     * <dd>{@code -}
     * <dd>{@code +}
     * </dl>
     * </blockquote>
     *
     * <i>DecimalNumeral</i>, <i>HexDigits</i>, and <i>OctalDigits</i>
     * are as defined in section 3.10.1 of
     * <cite>The Java&trade; Language Specification</cite>,
     * except that underscores are not accepted between digits.
     *
     * <p>The sequence of characters following an optional
     * sign and/or radix specifier ("{@code 0x}", "{@code 0X}",
     * "{@code #}", or leading zero) is parsed as by the {@code
     * Long.parseLong} method with the indicated radix (10, 16, or 8).
     * This sequence of characters must represent a positive value or
     * a {@link NumberFormatException} will be thrown.  The result is
     * negated if first character of the specified {@code String} is
     * the minus sign.  No whitespace characters are permitted in the
     * {@code String}.
     *
     * @param     nm the {@code String} to decode.
     * @return    a {@code Long} object holding the {@code long}
     *            value represented by {@code nm}
     * @throws    NumberFormatException  if the {@code String} does not
     *            contain a parsable {@code long}.
     * @see java.lang.Long#parseLong(String, int)
     * @since 1.2
     */
    public static Long decode(String nm) throws NumberFormatException {
        int radix = 10;
        int index = 0;
        boolean negative = false;
        Long result;

        if (nm.length() == 0)
            throw new NumberFormatException("Zero length string");
        char firstChar = nm.charAt(0);
        // Handle sign, if present
        if (firstChar == '-') {
            negative = true;
            index++;
        } else if (firstChar == '+')
            index++;

        // Handle radix specifier, if present
        if (nm.startsWith("0x", index) || nm.startsWith("0X", index)) {
            index += 2;
            radix = 16;
        }
        else if (nm.startsWith("#", index)) {
            index ++;
            radix = 16;
        }
        else if (nm.startsWith("0", index) && nm.length() > 1 + index) {
            index ++;
            radix = 8;
        }

        if (nm.startsWith("-", index) || nm.startsWith("+", index))
            throw new NumberFormatException("Sign character in wrong position");

        try {
            result = Long.valueOf(nm.substring(index), radix);
            // 这里精妙之处，Long.valueOf可以节省内存，使用缓存数据
            result = negative ? Long.valueOf(-result.longValue()) : result;
        } catch (NumberFormatException e) {
            // If number is Long.MIN_VALUE, we'll end up here. The next line
            // handles this case, and causes any genuine format error to be
            // rethrown.
            /**
             * 如果number为Long.MIN_VALUE，我们最终会在这里结束。
             * 下一行处理这种情况，并导致重新抛出任何真正的格式错误。
             */
            String constant = negative ? ("-" + nm.substring(index))
                                       : nm.substring(index);
            result = Long.valueOf(constant, radix);
        }
        return result;
    }

    /**
     * The value of the {@code Long}.
     *
     * @serial
     */
    // Long的值为final类型
    private final long value;

    /**
     * Constructs a newly allocated {@code Long} object that
     * represents the specified {@code long} argument.
     *
     * @param   value   the value to be represented by the
     *          {@code Long} object.
     */
    // 构造方法，生成一个Long的数据，最好使用Long.valueOf(),可以节省空间，
    public Long(long value) {
        this.value = value;
    }

    /**
     * Constructs a newly allocated {@code Long} object that
     * represents the {@code long} value indicated by the
     * {@code String} parameter. The string is converted to a
     * {@code long} value in exactly the manner used by the
     * {@code parseLong} method for radix 10.
     *
     * @param      s   the {@code String} to be converted to a
     *             {@code Long}.
     * @throws     NumberFormatException  if the {@code String} does not
     *             contain a parsable {@code long}.
     * @see        java.lang.Long#parseLong(java.lang.String, int)
     */
    public Long(String s) throws NumberFormatException {
        this.value = parseLong(s, 10);
    }

    /**
     * Returns the value of this {@code Long} as a {@code byte} after
     * a narrowing primitive conversion.
     * @jls 5.1.3 Narrowing Primitive Conversions
     */
    public byte byteValue() {
        return (byte)value;
    }

    /**
     * Returns the value of this {@code Long} as a {@code short} after
     * a narrowing primitive conversion.
     * @jls 5.1.3 Narrowing Primitive Conversions
     */
    public short shortValue() {
        return (short)value;
    }

    /**
     * Returns the value of this {@code Long} as an {@code int} after
     * a narrowing primitive conversion.
     * @jls 5.1.3 Narrowing Primitive Conversions
     */
    public int intValue() {
        return (int)value;
    }

    /**
     * Returns the value of this {@code Long} as a
     * {@code long} value.
     */
    public long longValue() {
        return value;
    }

    /**
     * Returns the value of this {@code Long} as a {@code float} after
     * a widening primitive conversion.
     * @jls 5.1.2 Widening Primitive Conversions
     */
    public float floatValue() {
        return (float)value;
    }

    /**
     * Returns the value of this {@code Long} as a {@code double}
     * after a widening primitive conversion.
     * @jls 5.1.2 Widening Primitive Conversions
     */
    public double doubleValue() {
        return (double)value;
    }

    /**
     * Returns a {@code String} object representing this
     * {@code Long}'s value.  The value is converted to signed
     * decimal representation and returned as a string, exactly as if
     * the {@code long} value were given as an argument to the
     * {@link java.lang.Long#toString(long)} method.
     *
     * @return  a string representation of the value of this object in
     *          base&nbsp;10.
     */
    public String toString() {
        return toString(value);
    }

    /**
     * Returns a hash code for this {@code Long}. The result is
     * the exclusive OR of the two halves of the primitive
     * {@code long} value held by this {@code Long}
     * object. That is, the hashcode is the value of the expression:
     *
     * <blockquote>
     *  {@code (int)(this.longValue()^(this.longValue()>>>32))}
     * </blockquote>
     *
     * @return  a hash code value for this object.
     */
    @Override
    public int hashCode() {
        return Long.hashCode(value);
    }

    /**
     * Returns a hash code for a {@code long} value; compatible with
     * {@code Long.hashCode()}.
     *
     * @param value the value to hash
     * @return a hash code value for a {@code long} value.
     * @since 1.8
     */
    public static int hashCode(long value) {
        return (int)(value ^ (value >>> 32));
    }

    /**
     * Compares this object to the specified object.  The result is
     * {@code true} if and only if the argument is not
     * {@code null} and is a {@code Long} object that
     * contains the same {@code long} value as this object.
     *
     * @param   obj   the object to compare with.
     * @return  {@code true} if the objects are the same;
     *          {@code false} otherwise.
     */
    // Long 的比较方法 ，先转化为拆箱类，再进行比较
    public boolean equals(Object obj) {
        if (obj instanceof Long) {
            return value == ((Long)obj).longValue();
        }
        return false;
    }

    /**
     * Determines the {@code long} value of the system property
     * with the specified name.
     *
     * <p>The first argument is treated as the name of a system
     * property.  System properties are accessible through the {@link
     * java.lang.System#getProperty(java.lang.String)} method. The
     * string value of this property is then interpreted as a {@code
     * long} value using the grammar supported by {@link Long#decode decode}
     * and a {@code Long} object representing this value is returned.
     *
     * <p>If there is no property with the specified name, if the
     * specified name is empty or {@code null}, or if the property
     * does not have the correct numeric format, then {@code null} is
     * returned.
     *
     * <p>In other words, this method returns a {@code Long} object
     * equal to the value of:
     *
     * <blockquote>
     *  {@code getLong(nm, null)}
     * </blockquote>
     *
     * @param   nm   property name.
     * @return  the {@code Long} value of the property.
     * @throws  SecurityException for the same reasons as
     *          {@link System#getProperty(String) System.getProperty}
     * @see     java.lang.System#getProperty(java.lang.String)
     * @see     java.lang.System#getProperty(java.lang.String, java.lang.String)
     */
    // 获取系统属性
    public static Long getLong(String nm) {
        return getLong(nm, null);
    }

    /**
     * Determines the {@code long} value of the system property
     * with the specified name.
     *
     * <p>The first argument is treated as the name of a system
     * property.  System properties are accessible through the {@link
     * java.lang.System#getProperty(java.lang.String)} method. The
     * string value of this property is then interpreted as a {@code
     * long} value using the grammar supported by {@link Long#decode decode}
     * and a {@code Long} object representing this value is returned.
     *
     * <p>The second argument is the default value. A {@code Long} object
     * that represents the value of the second argument is returned if there
     * is no property of the specified name, if the property does not have
     * the correct numeric format, or if the specified name is empty or null.
     *
     * <p>In other words, this method returns a {@code Long} object equal
     * to the value of:
     *
     * <blockquote>
     *  {@code getLong(nm, new Long(val))}
     * </blockquote>
     *
     * but in practice it may be implemented in a manner such as:
     *
     * <blockquote><pre>
     * Long result = getLong(nm, null);
     * return (result == null) ? new Long(val) : result;
     * </pre></blockquote>
     *
     * to avoid the unnecessary allocation of a {@code Long} object when
     * the default value is not needed.
     *
     * @param   nm    property name.
     * @param   val   default value.
     * @return  the {@code Long} value of the property.
     * @throws  SecurityException for the same reasons as
     *          {@link System#getProperty(String) System.getProperty}
     * @see     java.lang.System#getProperty(java.lang.String)
     * @see     java.lang.System#getProperty(java.lang.String, java.lang.String)
     */
    public static Long getLong(String nm, long val) {
        Long result = Long.getLong(nm, null);
        return (result == null) ? Long.valueOf(val) : result;
    }

    /**
     * Returns the {@code long} value of the system property with
     * the specified name.  The first argument is treated as the name
     * of a system property.  System properties are accessible through
     * the {@link java.lang.System#getProperty(java.lang.String)}
     * method. The string value of this property is then interpreted
     * as a {@code long} value, as per the
     * {@link Long#decode decode} method, and a {@code Long} object
     * representing this value is returned; in summary:
     *
     * <ul>
     * <li>If the property value begins with the two ASCII characters
     * {@code 0x} or the ASCII character {@code #}, not followed by
     * a minus sign, then the rest of it is parsed as a hexadecimal integer
     * exactly as for the method {@link #valueOf(java.lang.String, int)}
     * with radix 16.
     * <li>If the property value begins with the ASCII character
     * {@code 0} followed by another character, it is parsed as
     * an octal integer exactly as by the method {@link
     * #valueOf(java.lang.String, int)} with radix 8.
     * <li>Otherwise the property value is parsed as a decimal
     * integer exactly as by the method
     * {@link #valueOf(java.lang.String, int)} with radix 10.
     * </ul>
     *
     * <p>Note that, in every case, neither {@code L}
     * ({@code '\u005Cu004C'}) nor {@code l}
     * ({@code '\u005Cu006C'}) is permitted to appear at the end
     * of the property value as a type indicator, as would be
     * permitted in Java programming language source code.
     *
     * <p>The second argument is the default value. The default value is
     * returned if there is no property of the specified name, if the
     * property does not have the correct numeric format, or if the
     * specified name is empty or {@code null}.
     *
     * @param   nm   property name.
     * @param   val   default value.
     * @return  the {@code Long} value of the property.
     * @throws  SecurityException for the same reasons as
     *          {@link System#getProperty(String) System.getProperty}
     * @see     System#getProperty(java.lang.String)
     * @see     System#getProperty(java.lang.String, java.lang.String)
     */
    // 获取系统属性
    public static Long getLong(String nm, Long val) {
        String v = null;
        try {
            v = System.getProperty(nm);
        } catch (IllegalArgumentException | NullPointerException e) {
        }
        if (v != null) {
            try {
                return Long.decode(v);
            } catch (NumberFormatException e) {
            }
        }
        return val;
    }

    /**
     * Compares two {@code Long} objects numerically.
     *
     * @param   anotherLong   the {@code Long} to be compared.
     * @return  the value {@code 0} if this {@code Long} is
     *          equal to the argument {@code Long}; a value less than
     *          {@code 0} if this {@code Long} is numerically less
     *          than the argument {@code Long}; and a value greater
     *          than {@code 0} if this {@code Long} is numerically
     *           greater than the argument {@code Long} (signed
     *           comparison).
     * @since   1.2
     */
    // 在类内部也可以这样用anotherLong.value，即使是传过来的参数
    public int compareTo(Long anotherLong) {
        return compare(this.value, anotherLong.value);
    }

    /**
     * Compares two {@code long} values numerically.
     * The value returned is identical to what would be returned by:
     * <pre>
     *    Long.valueOf(x).compareTo(Long.valueOf(y))
     * </pre>
     *
     * @param  x the first {@code long} to compare
     * @param  y the second {@code long} to compare
     * @return the value {@code 0} if {@code x == y};
     *         a value less than {@code 0} if {@code x < y}; and
     *         a value greater than {@code 0} if {@code x > y}
     * @since 1.7
     */
    /**
     * 比较两个数
     */
    public static int compare(long x, long y) {
        return (x < y) ? -1 : ((x == y) ? 0 : 1);
    }

    /**
     * Compares two {@code long} values numerically treating the values
     * as unsigned.
     *
     * @param  x the first {@code long} to compare
     * @param  y the second {@code long} to compare
     * @return the value {@code 0} if {@code x == y}; a value less
     *         than {@code 0} if {@code x < y} as unsigned values; and
     *         a value greater than {@code 0} if {@code x > y} as
     *         unsigned values
     * @since 1.8
     */
    // 以x + MIN_VALUE方式转化为同符号的
    public static int compareUnsigned(long x, long y) {
        return compare(x + MIN_VALUE, y + MIN_VALUE);
    }


    /**
     * Returns the unsigned quotient of dividing the first argument by
     * the second where each argument and the result is interpreted as
     * an unsigned value.
     *
     * <p>Note that in two's complement arithmetic, the three other
     * basic arithmetic operations of add, subtract, and multiply are
     * bit-wise identical if the two operands are regarded as both
     * being signed or both being unsigned.  Therefore separate {@code
     * addUnsigned}, etc. methods are not provided.
     *
     * @param dividend the value to be divided
     * @param divisor the value doing the dividing
     * @return the unsigned quotient of the first argument divided by
     * the second argument
     * @see #remainderUnsigned
     * @since 1.8
     */
    /**
     * 注意，在二进制补码算法中，如果两个操作数被认为都是有符号的或两者都是无符号的，
     * 则加、减和乘的另外三个基本算术运算是按位相同的。
     * 因此，不提供单独的{@code addUnsigned}等方法。
     */
    public static long divideUnsigned(long dividend, long divisor) {
        if (divisor < 0L) { // signed comparison
            // Answer must be 0 or 1 depending on relative magnitude
            // of dividend and divisor.
            return (compareUnsigned(dividend, divisor)) < 0 ? 0L :1L;
        }

        if (dividend > 0) //  Both inputs non-negative
            return dividend/divisor;
        else {
            /*
             * For simple code, leveraging BigInteger.  Longer and faster
             * code written directly in terms of operations on longs is
             * possible; see "Hacker's Delight" for divide and remainder
             * algorithms.
             */
            return toUnsignedBigInteger(dividend).
                divide(toUnsignedBigInteger(divisor)).longValue();
        }
    }

    /**
     * Returns the unsigned remainder from dividing the first argument
     * by the second where each argument and the result is interpreted
     * as an unsigned value.
     *
     * @param dividend the value to be divided
     * @param divisor the value doing the dividing
     * @return the unsigned remainder of the first argument divided by
     * the second argument
     * @see #divideUnsigned
     * @since 1.8
     */
    // 无符号求余
    public static long remainderUnsigned(long dividend, long divisor) {
        if (dividend > 0 && divisor > 0) { // signed comparisons
            return dividend % divisor;
        } else {                                       // dividend divisor两个中至少有一个不大于0
            // dividend < divisor
            if (compareUnsigned(dividend, divisor) < 0) // Avoid explicit check for 0 divisor
                return dividend;
            else
                return toUnsignedBigInteger(dividend).
                    remainder(toUnsignedBigInteger(divisor)).longValue();
        }
    }

    // Bit Twiddling

    /**
     * The number of bits used to represent a {@code long} value in two's
     * complement binary form.
     *
     * @since 1.5
     */
    // 二进制位数
    @Native public static final int SIZE = 64;

    /**
     * The number of bytes used to represent a {@code long} value in two's
     * complement binary form.
     *
     * @since 1.8
     */
    // 字节数
    public static final int BYTES = SIZE / Byte.SIZE;

    /**
     * Returns a {@code long} value with at most a single one-bit, in the
     * position of the highest-order ("leftmost") one-bit in the specified
     * {@code long} value.  Returns zero if the specified value has no
     * one-bits in its two's complement binary representation, that is, if it
     * is equal to zero.
     *
     * @param i the value whose highest one bit is to be computed
     * @return a {@code long} value with a single one-bit, in the position
     *     of the highest-order one-bit in the specified value, or zero if
     *     the specified value is itself equal to zero.
     * @since 1.5
     */
    // 获取二进制最高位的值
    public static long highestOneBit(long i) {
        // HD, Figure 3-1
        /**
         * 63位
         * 下面这些移位再或运算为了让位数全部为1，
         */
        i |= (i >>  1);
        i |= (i >>  2);
        i |= (i >>  4);
        i |= (i >>  8);
        i |= (i >> 16);
        i |= (i >> 32);

        // 去掉最高位，再相减获取最高位的值
        return i - (i >>> 1);
    }

    /**
     * Returns a {@code long} value with at most a single one-bit, in the
     * position of the lowest-order ("rightmost") one-bit in the specified
     * {@code long} value.  Returns zero if the specified value has no
     * one-bits in its two's complement binary representation, that is, if it
     * is equal to zero.
     *
     * @param i the value whose lowest one bit is to be computed
     * @return a {@code long} value with a single one-bit, in the position
     *     of the lowest-order one-bit in the specified value, or zero if
     *     the specified value is itself equal to zero.
     * @since 1.5
     */
    public static long lowestOneBit(long i) {
        // HD, Section 2-1
        return i & -i;
    }

    /**
     * Returns the number of zero bits preceding the highest-order
     * ("leftmost") one-bit in the two's complement binary representation
     * of the specified {@code long} value.  Returns 64 if the
     * specified value has no one-bits in its two's complement representation,
     * in other words if it is equal to zero.
     *
     * <p>Note that this method is closely related to the logarithm base 2.
     * For all positive {@code long} values x:
     * <ul>
     * <li>floor(log<sub>2</sub>(x)) = {@code 63 - numberOfLeadingZeros(x)}
     * <li>ceil(log<sub>2</sub>(x)) = {@code 64 - numberOfLeadingZeros(x - 1)}
     * </ul>
     *
     * @param i the value whose number of leading zeros is to be computed
     * @return the number of zero bits preceding the highest-order
     *     ("leftmost") one-bit in the two's complement binary representation
     *     of the specified {@code long} value, or 64 if the value
     *     is equal to zero.
     * @since 1.5
     */
    /**
     * 返回指定long值的二进制补码二进制表示中最高位（“最左侧”）一位之前的零位数。
     * 如果指定的值在其二进制补码表示中没有一位，则返回64，换句话说，如果它等于零。
     *
     *
     * <<      :     左移运算符，num << 1,相当于num乘以2
     *
     * >>      :     右移运算符，num >> 1,相当于num除以2
     *
     * >>>    :     无符号右移，忽略符号位，空位都以0补齐
     *
     * 无符号右移运算符>>> 只是对32位和64位的值有意
     *
     * 分析：把64位类型，先32截取，在根据32位进行相应的运算
     *
     */
    public static int numberOfLeadingZeros(long i) {
        // HD, Figure 5-6
         if (i == 0)
            return 64;
        int n = 1;
        // 右移32
        int x = (int)(i >>> 32);

        /**
         * 如果i小于32，那么结果是0，重新赋值x，n= 33 => 1+32,
         * 大于32位，n = 1
         */
        if (x == 0) { n += 32; x = (int)i; }
        /**
         * 如果右移16，i小于16，那么结果是0，左移16赋值给x，n= 49  => 33+16，，
         * 大于16位，小于32位， n = 33
         * 大于32位，n = 1
         */
        if (x >>> 16 == 0) { n += 16; x <<= 16; }
        /**
         * 因为上面左移了16位了，再右移24，结果还是为0，那么x小于8位， n = 57 => 49 + 8
         * 如果未进行上面16位移动，那么就大于16位
         * 下面以此类推
         */
        if (x >>> 24 == 0) { n +=  8; x <<=  8; }

        /**
         * n = 61 => 57 + 4
         */
        if (x >>> 28 == 0) { n +=  4; x <<=  4; }

        /**
         * 如果x为1的话，n = 63 => 61 + 2 ,
         */
        if (x >>> 30 == 0) { n +=  2; x <<=  2; }

        /**
         * 上面对x移动的位数为  30 = 16+8+4+2，
         * 再移动31位，
         */
        n -= x >>> 31;
        return n;
    }

    /**
     * Returns the number of zero bits following the lowest-order ("rightmost")
     * one-bit in the two's complement binary representation of the specified
     * {@code long} value.  Returns 64 if the specified value has no
     * one-bits in its two's complement representation, in other words if it is
     * equal to zero.
     *
     * @param i the value whose number of trailing zeros is to be computed
     * @return the number of zero bits following the lowest-order ("rightmost")
     *     one-bit in the two's complement binary representation of the
     *     specified {@code long} value, or 64 if the value is equal
     *     to zero.
     * @since 1.5
     */
    // 返回指定{@code long}值的二进制补码二进制表示中最低位（“最右侧”）一位之后的零位数
    public static int numberOfTrailingZeros(long i) {
        // HD, Figure 5-14
        int x, y;
        if (i == 0) return 64;
        int n = 63;
        // i为long类型，强制类型转化 ？？？
        y = (int)i; if (y != 0) { n = n -32; x = y; } else x = (int)(i>>>32);
        y = x <<16; if (y != 0) { n = n -16; x = y; }
        y = x << 8; if (y != 0) { n = n - 8; x = y; }
        y = x << 4; if (y != 0) { n = n - 4; x = y; }
        y = x << 2; if (y != 0) { n = n - 2; x = y; }
        return n - ((x << 1) >>> 31);
    }

    /**
     * Returns the number of one-bits in the two's complement binary
     * representation of the specified {@code long} value.  This function is
     * sometimes referred to as the <i>population count</i>.
     *
     * @param i the value whose bits are to be counted
     * @return the number of one-bits in the two's complement binary
     *     representation of the specified {@code long} value.
     * @since 1.5
     */
    // ???
     public static int bitCount(long i) {
        // HD, Figure 5-14
        i = i - ((i >>> 1) & 0x5555555555555555L);
        i = (i & 0x3333333333333333L) + ((i >>> 2) & 0x3333333333333333L);
        i = (i + (i >>> 4)) & 0x0f0f0f0f0f0f0f0fL;
        i = i + (i >>> 8);
        i = i + (i >>> 16);
        i = i + (i >>> 32);
        return (int)i & 0x7f;
     }

    /**
     * Returns the value obtained by rotating the two's complement binary
     * representation of the specified {@code long} value left by the
     * specified number of bits.  (Bits shifted out of the left hand, or
     * high-order, side reenter on the right, or low-order.)
     *
     * <p>Note that left rotation with a negative distance is equivalent to
     * right rotation: {@code rotateLeft(val, -distance) == rotateRight(val,
     * distance)}.  Note also that rotation by any multiple of 64 is a
     * no-op, so all but the last six bits of the rotation distance can be
     * ignored, even if the distance is negative: {@code rotateLeft(val,
     * distance) == rotateLeft(val, distance & 0x3F)}.
     *
     * @param i the value whose bits are to be rotated left
     * @param distance the number of bit positions to rotate left
     * @return the value obtained by rotating the two's complement binary
     *     representation of the specified {@code long} value left by the
     *     specified number of bits.
     * @since 1.5
     */
    // 请注意，具有负距离的左旋转等效于右旋转：
    // {@ code rotateLeft（val，-distance）== rotateRight（val，distance）}。
    public static long rotateLeft(long i, int distance) {
        return (i << distance) | (i >>> -distance);
    }

    /**
     * Returns the value obtained by rotating the two's complement binary
     * representation of the specified {@code long} value right by the
     * specified number of bits.  (Bits shifted out of the right hand, or
     * low-order, side reenter on the left, or high-order.)
     *
     * <p>Note that right rotation with a negative distance is equivalent to
     * left rotation: {@code rotateRight(val, -distance) == rotateLeft(val,
     * distance)}.  Note also that rotation by any multiple of 64 is a
     * no-op, so all but the last six bits of the rotation distance can be
     * ignored, even if the distance is negative: {@code rotateRight(val,
     * distance) == rotateRight(val, distance & 0x3F)}.
     *
     * @param i the value whose bits are to be rotated right
     * @param distance the number of bit positions to rotate right
     * @return the value obtained by rotating the two's complement binary
     *     representation of the specified {@code long} value right by the
     *     specified number of bits.
     * @since 1.5
     */
    public static long rotateRight(long i, int distance) {
        return (i >>> distance) | (i << -distance);
    }

    /**
     * Returns the value obtained by reversing the order of the bits in the
     * two's complement binary representation of the specified {@code long}
     * value.
     *
     * @param i the value to be reversed
     * @return the value obtained by reversing order of the bits in the
     *     specified {@code long} value.
     * @since 1.5
     */
    // ???
    public static long reverse(long i) {
        // HD, Figure 7-1
        i = (i & 0x5555555555555555L) << 1 | (i >>> 1) & 0x5555555555555555L;
        i = (i & 0x3333333333333333L) << 2 | (i >>> 2) & 0x3333333333333333L;
        i = (i & 0x0f0f0f0f0f0f0f0fL) << 4 | (i >>> 4) & 0x0f0f0f0f0f0f0f0fL;
        i = (i & 0x00ff00ff00ff00ffL) << 8 | (i >>> 8) & 0x00ff00ff00ff00ffL;
        i = (i << 48) | ((i & 0xffff0000L) << 16) |
            ((i >>> 16) & 0xffff0000L) | (i >>> 48);
        return i;
    }

    /**
     * Returns the signum function of the specified {@code long} value.  (The
     * return value is -1 if the specified value is negative; 0 if the
     * specified value is zero; and 1 if the specified value is positive.)
     *
     * @param i the value whose signum is to be computed
     * @return the signum function of the specified {@code long} value.
     * @since 1.5
     */
    /**
     * 如果指定的值为负，则*返回值为-1;
     * 如果指定的值为零，则返回0;
     * 如果指定的值为正，则返回1。
     */
    public static int signum(long i) {
        // HD, Section 2-7
        return (int) ((i >> 63) | (-i >>> 63));
    }

    /**
     * Returns the value obtained by reversing the order of the bytes in the
     * two's complement representation of the specified {@code long} value.
     *
     * @param i the value whose bytes are to be reversed
     * @return the value obtained by reversing the bytes in the specified
     *     {@code long} value.
     * @since 1.5
     */
    public static long reverseBytes(long i) {
        i = (i & 0x00ff00ff00ff00ffL) << 8 | (i >>> 8) & 0x00ff00ff00ff00ffL;
        return (i << 48) | ((i & 0xffff0000L) << 16) |
            ((i >>> 16) & 0xffff0000L) | (i >>> 48);
    }

    /**
     * Adds two {@code long} values together as per the + operator.
     *
     * @param a the first operand
     * @param b the second operand
     * @return the sum of {@code a} and {@code b}
     * @see java.util.function.BinaryOperator
     * @since 1.8
     */
    public static long sum(long a, long b) {
        return a + b;
    }

    /**
     * Returns the greater of two {@code long} values
     * as if by calling {@link Math#max(long, long) Math.max}.
     *
     * @param a the first operand
     * @param b the second operand
     * @return the greater of {@code a} and {@code b}
     * @see java.util.function.BinaryOperator
     * @since 1.8
     */
    public static long max(long a, long b) {
        return Math.max(a, b);
    }

    /**
     * Returns the smaller of two {@code long} values
     * as if by calling {@link Math#min(long, long) Math.min}.
     *
     * @param a the first operand
     * @param b the second operand
     * @return the smaller of {@code a} and {@code b}
     * @see java.util.function.BinaryOperator
     * @since 1.8
     */
    public static long min(long a, long b) {
        return Math.min(a, b);
    }

    /** use serialVersionUID from JDK 1.0.2 for interoperability */
    @Native private static final long serialVersionUID = 4290774380558885855L;
}
