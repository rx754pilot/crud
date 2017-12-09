package com.crud.portal;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public final class QueryUtil
{

    

    public static boolean isColumnAvailable(String columnName, ResultSet rs)
    {
        boolean flag = false;
        try
        {
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            for (int i = 0; i < columnCount; i++)
            {
                String columnLabel = metaData.getColumnLabel(i);
                if (columnName.equalsIgnoreCase(columnLabel))
                {
                    flag = true;
                    break;
                }
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return flag;
    }

}
