package com.remobile.moment;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@SuppressWarnings({"WeakerAccess", "UnusedReturnValue"})
public class Moment implements Cloneable, Serializable, Comparable<Moment> {
    private static final long serialVersionUID = 1L;
    private final Calendar calendar;
    private static final Map<String, Integer> CALENDAR_FIELD = new HashMap() {{
        put("year", Calendar.YEAR);
        put("years", Calendar.YEAR);
        put("y", Calendar.YEAR);
        put("month", Calendar.MONTH);
        put("months", Calendar.MONTH);
        put("M", Calendar.MONTH);
        put("date", Calendar.DATE);
        put("days", Calendar.DATE);
        put("d", Calendar.DATE);
        put("hour", Calendar.HOUR);
        put("minute", Calendar.MINUTE);
        put("minutes", Calendar.MINUTE);
        put("m", Calendar.MINUTE);
        put("second", Calendar.SECOND);
        put("seconds", Calendar.SECOND);
        put("s", Calendar.SECOND);
        put("millisecond", Calendar.MILLISECOND);
        put("milliseconds", Calendar.MILLISECOND);
        put("ms", Calendar.MILLISECOND);
    }};

    // 构造函数
    private Moment() {
        this.calendar = Calendar.getInstance();
    }
    private Moment(String dateStr, String pattern) {
        this();
        Date date;
        try {
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            date = format.parse(dateStr);
        } catch (Exception e) {
            throw new RuntimeException("Parse error occurred while parsing [" + dateStr + "] with SimpleDateFormat [" + pattern + "]", e);
        }
        this.calendar.setTime(date);
    }
    private Moment(String dateStr) {
        this();
        String pattern = "yyyy-MM-dd hh:mm:ss";
        if (dateStr.length() != pattern.length()) {
            pattern = "yyyy-MM-dd hh:mm:ss.SSS";
            if (dateStr.length() != pattern.length()) {
                pattern = "yyyy-MM-dd";
            }
        }
        Date date;
        try {
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            date = format.parse(dateStr);
        } catch (Exception e) {
            throw new RuntimeException("Parse error occurred while parsing [" + dateStr + "] with SimpleDateFormat [" + pattern + "]", e);
        }
        this.calendar.setTime(date);
    }
    private Moment(Calendar calendar) {
        this.calendar = (Calendar) calendar.clone();
    }
    private Moment(Date date) {
        this();
        this.calendar.setTimeInMillis(date.getTime());
    }
    private Moment(long timeInMillis) {
        this();
        this.calendar.setTimeInMillis(timeInMillis);
    }
    // Moment moment = Moment.moment([years, months, days, hours, minutes, seconds, and milliseconds]);
    private Moment(int[] array) {
        this();

        if (array == null) {
            throw new RuntimeException("int[] array parameter cannot be null!");
        }

        if (array.length != 7) {
            throw new RuntimeException("int[] array must have exactly 7 elements! You provided " + array.length);
        }

        this.calendar.set(Calendar.YEAR, array[0]);
        this.calendar.set(Calendar.MONTH, array[1]);
        this.calendar.set(Calendar.DAY_OF_MONTH, array[2]);
        this.calendar.set(Calendar.HOUR_OF_DAY, array[3]);
        this.calendar.set(Calendar.MINUTE, array[4]);
        this.calendar.set(Calendar.SECOND, array[5]);
        this.calendar.set(Calendar.MILLISECOND, array[6]);
    }




    // 取值/赋值
    public Moment millisecond(int millisecond) {
        this.calendar.set(Calendar.MILLISECOND, millisecond);
        return this;
    }
    public int millisecond() {
        return this.calendar.get(Calendar.MILLISECOND);
    }
    public Moment second(int second) {
        this.calendar.set(Calendar.SECOND, second);
        return this;
    }
    public int second() {
        return this.calendar.get(Calendar.SECOND);
    }
    public Moment minute(int minute) {
        this.calendar.set(Calendar.MINUTE, minute);
        return this;
    }
    public int minute() {
        return this.calendar.get(Calendar.MINUTE);
    }
    public Moment hour(int hour) {
        this.calendar.set(Calendar.HOUR_OF_DAY, hour);
        return this;
    }
    public int hour() {
        return this.calendar.get(Calendar.HOUR_OF_DAY);
    }
    public Moment date(int dayOfMonth) {
        this.calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        return this;
    }
    public int date() {
        return this.calendar.get(Calendar.DAY_OF_MONTH);
    }
    public Moment day(int dayOfWeek) {
        this.calendar.set(Calendar.DAY_OF_WEEK, dayOfWeek);
        return this;
    }
    public int day() {
        return this.calendar.get(Calendar.DAY_OF_WEEK);
    }
    public Moment month(int month) {
        this.calendar.set(Calendar.MONTH, month);
        return this;
    }
    public int month() {
        return this.calendar.get(Calendar.MONTH);
    }
    public Moment year(int year) {
        this.calendar.set(Calendar.YEAR, year);
        return this;
    }
    public int year() {
        return this.calendar.get(Calendar.YEAR);
    }
    public Moment dayOfYear(int dayOfYear) {
        this.calendar.set(Calendar.DAY_OF_YEAR, dayOfYear);
        return this;
    }
    public int dayOfYear() {
        return this.calendar.get(Calendar.DAY_OF_YEAR);
    }

    public Moment set(String field, int value) {
        this.calendar.set(CALENDAR_FIELD.get(field), value);
        return this;
    }
    public int get(String field) {
        return this.calendar.get(CALENDAR_FIELD.get(field));
    }

    // 操作
    public Moment add(int value, String field) {
        this.calendar.add(CALENDAR_FIELD.get(field), value);
        return this;
    }
    public Moment subtract(int value, String field) {
        this.calendar.add(CALENDAR_FIELD.get(field), -value);
        return this;
    }
    public Moment startOf(String field) {
        switch (field) {
            case "year":
            case "y":
                this.month(0);
            case "month":
            case "M":
                this.date(1);
            case "week":
            case "w":
                this.day(1);
            case "day":
            case "d":
                this.hour(0);
            case "hour":
            case "h":
                this.minute(0);
            case "minute":
            case "m":
                this.second(0);
            case "second":
            case "s":
                this.millisecond(0);
                break;
            default:
                throw new RuntimeException("Unknown calendarField: " + field);
        }
        return this;
    }
    public Moment endOf(String field) {
        return this.startOf(field).add(1, field).subtract(1, "ms");
    }
    public static Moment max(Moment... moments) {
        if (moments == null || moments.length == 0) {
            return moment();
        }
        Moment max = moments[0];
        for (int i = 1; i < moments.length; i++) {
            if (moments[i].valueOf() > max.valueOf()) {
                max = moments[i];
            }
        }
        return max;
    }
    public static Moment min(Moment... moments) {
        if (moments == null || moments.length == 0) {
            return moment();
        }
        Moment min = moments[0];
        for (int i = 1; i < moments.length; i++) {
            if (moments[i].valueOf() < min.valueOf()) {
                min = moments[i];
            }
        }
        return min;
    }

    // 显示
    // moment().format("yyyy-MM-dd HH:mm:ss.SSS");
    public String format(String pattern) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            return format.format(calendar.getTime());
        } catch (Exception e) {
            throw new RuntimeException("Parse error occurred while formatting [" + calendar + "] with SimpleDateFormat [" + pattern + "]", e);
        }
    }
    public long valueOf() {
        return this.calendar.getTimeInMillis();
    }
    public long unix() {
        return valueOf() / 1000;
    }
    public float diff(Moment moment) {
        return this.diff(moment, 1, "ms");
    }
    public float diff(Moment moment, String field) {
        return this.diff(moment, 1, field);
    }
    public float diff(Moment moment,int value, String field) {
        return this._diff(moment, value, field);
    }
    private float _diff(Moment moment,int value, String field) {
        long ms1 = this.valueOf();
        long ms2 = moment.valueOf();

        switch (field) {
            case "years":
            case "y":
                return this.monthDiff(this, moment) / 12;
            case "quarters":
            case "Q":
                return this.monthDiff(this, moment) / 3;
            case "months":
            case "M":
                return this.monthDiff(this, moment);
            case "weeks":
            case "w":
                return (ms1 - ms2) / 604800000;
            case "days":
            case "d":
                return (ms1 - ms2) / 86400000;
            case "hours":
            case "h":
                return (ms1 - ms2) / 3600000;
            case "minutes":
            case "m":
                return (ms1 - ms2) / 60000;
            case "seconds":
            case "s":
                return (ms1 - ms2) / 1000;
            case "milliseconds":
            case "ms":
                return ms1 - ms2;
            default:
                throw new RuntimeException("Unknown calendarField: " + field);
        }
    }
    private float monthDiff (Moment a, Moment b) {
        int wholeMonthDiff = ((b.year() - a.year()) * 12) + (b.month() - a.month());
        Moment anchor = a.clone().add(wholeMonthDiff, "months");
        Moment anchor2;
        float adjust;
        if (b.valueOf() < anchor.valueOf()) {
            anchor2 = anchor.clone().subtract(1, "months");
            adjust = (float)(b.valueOf() - anchor.valueOf()) / (anchor.valueOf() - anchor2.valueOf());
        } else {
            anchor2 = anchor.clone().add(1, "months");
            adjust = (float)(b.valueOf() - anchor.valueOf()) / (anchor2.valueOf() - anchor.valueOf());
        }
        return -(wholeMonthDiff + adjust);
    }
    public int daysInMonth() {
        return this.month() == 1 ? (isLeapYear(this.year()) ? 29 : 28) : (31 - this.month() % 7 % 2);
    }
    public Date toDate() {
        return this.calendar.getTime();
    }
    public int[] toArray() {
        int[] array = new int[7];
        array[0] = this.calendar.get(Calendar.YEAR);
        array[1] = this.calendar.get(Calendar.MONTH);
        array[2] = this.calendar.get(Calendar.DAY_OF_MONTH);
        array[3] = this.calendar.get(Calendar.HOUR_OF_DAY);
        array[4] = this.calendar.get(Calendar.MINUTE);
        array[5] = this.calendar.get(Calendar.SECOND);
        array[6] = this.calendar.get(Calendar.MILLISECOND);
        return array;
    }


    // 查询
    // --- isBefore
    public boolean isBefore(Moment moment) {
        return isBefore(moment.valueOf());
    }
    public boolean isBefore(Moment moment, String field) {
        return isBefore(moment.valueOf(), field);
    }
    public boolean isBefore(String dateStr) {
        return isBefore(new Moment(dateStr));
    }
    public boolean isBefore(String dateStr, String field) {
        return isBefore(new Moment(dateStr), field);
    }
    public boolean isBefore(long milliseconds) {
        return this.valueOf() < milliseconds;
    }
    public boolean isBefore(long milliseconds, String field) {
        if (field == "millisecond" || field == "ms") {
            return this.valueOf() < milliseconds;
        } else {
            return this.clone().endOf(field).valueOf() < milliseconds;
        }
    }
    public boolean isBefore(Date date) {
        return isBefore(date.getTime());
    }
    public boolean isBefore(Date date, String field) {
        return isBefore(date.getTime(), field);
    }
    public boolean isBefore(Calendar calendar) {
        return isBefore(calendar.getTimeInMillis());
    }
    public boolean isBefore(Calendar calendar, String field) {
        return isBefore(calendar.getTimeInMillis(), field);
    }
    // --- isAfter
    public boolean isAfter(Moment moment) {
        return isAfter(moment.valueOf());
    }
    public boolean isAfter(Moment moment, String field) {
        return isAfter(moment.valueOf(), field);
    }
    public boolean isAfter(String dateStr) {
        return isAfter(new Moment(dateStr));
    }
    public boolean isAfter(String dateStr, String field) {
        return isAfter(new Moment(dateStr), field);
    }
    public boolean isAfter(long milliseconds) {
        return this.valueOf() > milliseconds;
    }
    public boolean isAfter(long milliseconds, String field) {
        if (field == "millisecond" || field == "ms") {
            return this.valueOf() > milliseconds;
        } else {
            return this.clone().startOf(field).valueOf() > milliseconds;
        }
    }
    public boolean isAfter(Date date) {
        return isAfter(date.getTime());
    }
    public boolean isAfter(Date date, String field) {
        return isAfter(date.getTime(), field);
    }
    public boolean isAfter(Calendar calendar) {
        return isAfter(calendar.getTimeInMillis());
    }
    public boolean isAfter(Calendar calendar, String field) {
        return isAfter(calendar.getTimeInMillis(), field);
    }
    // --- isSame
    public boolean isSame(Moment moment) {
        return isSame(moment.valueOf());
    }
    public boolean isSame(Moment moment, String field) {
        return isSame(moment.valueOf(), field);
    }
    public boolean isSame(String dateStr) {
        return isSame(new Moment(dateStr));
    }
    public boolean isSame(String dateStr, String field) {
        return isSame(new Moment(dateStr), field);
    }
    public boolean isSame(long milliseconds) {
        return this.valueOf() == milliseconds;
    }
    public boolean isSame(long milliseconds, String field) {
        if (field == "millisecond" || field == "ms") {
            return this.valueOf() == milliseconds;
        } else {
            return this.clone().startOf(field).valueOf() <= milliseconds && milliseconds <= this.clone().endOf(field).valueOf();
        }
    }
    public boolean isSame(Date date) {
        return isSame(date.getTime());
    }
    public boolean isSame(Date date, String field) {
        return isSame(date.getTime(), field);
    }
    public boolean isSame(Calendar calendar) {
        return isSame(calendar.getTimeInMillis());
    }
    public boolean isSame(Calendar calendar, String field) {
        return isSame(calendar.getTimeInMillis(), field);
    }
    // --- isBetween
    public boolean isBetween(Moment from, Moment to) {
        return isAfter(from) && isBefore(to);
    }
    public boolean isBetween(Moment from, Moment to, String field) {
        return isAfter(from, field) && isBefore(to, field);
    }
    public boolean isBetween(String from, String to) {
        return isAfter(from) && isBefore(to);
    }
    public boolean isBetween(String from, String to, String field) {
        return isAfter(from, field) && isBefore(to, field);
    }
    public boolean isBetween(long fromMillis, long toMillis) {
        return isAfter(fromMillis) && isBefore(toMillis);
    }
    public boolean isBetween(long fromMillis, long toMillis, String field) {
        return isAfter(fromMillis, field) && isBefore(toMillis, field);
    }
    public boolean isBetween(Date from, Date to) {
        return isAfter(from) && isBefore(to);
    }
    public boolean isBetween(Date from, Date to, String field) {
        return isAfter(from, field) && isBefore(to, field);
    }
    public boolean isBetween(Calendar from, Calendar to) {
        return isAfter(from) && isBefore(to);
    }
    public boolean isBetween(Calendar from, Calendar to, String field) {
        return isAfter(from, field) && isBefore(to, field);
    }
    // --- isLeapYear
    public boolean isLeapYear() {
        return isLeapYear(year());
    }

    // static
    public static boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || year % 400 == 0;
    }
    public static Moment moment() {
        return new Moment();
    }
    public static Moment moment(String dateStr, String pattern) {
        return new Moment(dateStr, pattern);
    }
    public static Moment moment(String dateStr) {
            return new Moment(dateStr);
    }
    public static Moment moment(Calendar calendar) {
        return new Moment(calendar);
    }
    public static Moment moment(Date date) {
        return new Moment(date);
    }
    public static Moment moment(long timeInMillis) {
        return new Moment(timeInMillis);
    }
    public static Moment moment(int[] array) {
        return new Moment(array);
    }
    public static Moment moment(Moment source) {
        return new Moment(source.valueOf());
    }

    // Override
    @Override
    public Moment clone() {
        return new Moment((Calendar) this.calendar.clone());
    }
    @Override
    public int hashCode() {
        Long value = valueOf();
        return value.hashCode();
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Moment other = (Moment) obj;

        return isSame(other);
    }
    @Override
    public int compareTo(Moment moment) {
        return Long.compare(this.valueOf(), moment.valueOf());
    }
    @Override
    @SuppressWarnings("StringBufferReplaceableByString")
    public String toString() {
        return format("YYYY-MM-DD HH:mm:ss.SSS");
    }
}
