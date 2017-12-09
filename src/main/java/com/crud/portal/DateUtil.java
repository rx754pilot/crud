package com.crud.portal;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

import org.joda.time.Days;

import com.jedlab.framework.util.JalaliCalendar;

public final class DateUtil
{

    public final static Date addDate(Date date, int field, int amount)
    {
        Calendar cal = new JalaliCalendar();
        cal.setTime(date);
        cal.add(field, amount);
        return cal.getTime();
    }

    public static Date truncateDateTime(Date date)
    {
        if (date == null)
            return date;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
    
    
    public static Date importDateTime()
    {            
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.YEAR, 2000);
        calendar.set(Calendar.MONTH, 1);
        calendar.set(Calendar.DATE, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
    
    public static String subtract(Date d1, Date d2)
    {
//        Calendar c1 = Calendar.getInstance();
//        c1.setTime(d1);
//        Calendar c2 = Calendar.getInstance();
//        c2.setTime(d2);
//        long mil = d2.getTime() - d1.getTime();
        LocalDate date1 = d1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate date2 = d2.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Period p = Period.between(date1, date2);
        StringBuilder sb = new StringBuilder();
        if(p.getYears() > 0)
            sb.append(p.getYears()).append(" \u0633\u0627\u0644 ");
        if(p.getMonths() > 0)
            sb.append(p.getMonths()).append(" \u0645\u0627\u0647 ");
        if(p.getDays() > 0)
            sb.append(p.getDays()).append(" \u0631\u0648\u0632 ");
        return  sb.toString();
    }
    
    
}
