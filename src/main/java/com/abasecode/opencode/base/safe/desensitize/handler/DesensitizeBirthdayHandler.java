package com.abasecode.opencode.base.safe.desensitize.handler;


import com.abasecode.opencode.base.safe.desensitize.DesensitizeBirthday;

import com.abasecode.opencode.base.safe.util.CodeDesensitizeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.springframework.stereotype.Component;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Jon
 * e-mail: ijonso123@gmail.com
 * url: <a href="https://jon.wiki">Jon's blog</a>
 * url: <a href="https://github.com/abasecode">project github</a>
 * url: <a href="https://abasecode.com">AbaseCode.com</a>
 */
@MappedTypes(DesensitizeBirthday.class)
@Slf4j
@Component
public class DesensitizeBirthdayHandler extends BaseTypeHandler<String> {

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, String s, JdbcType jdbcType) throws SQLException {
        try{
            String s1 = CodeDesensitizeUtils.desensitizeBirthday(s);
            preparedStatement.setString(i,s1);
        }catch (Exception e){
            preparedStatement.setString(i,s);
        }
    }

    @Override
    public String getNullableResult(ResultSet resultSet, String s) throws SQLException {
        return resultSet.getString(s);
    }

    @Override
    public String getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return null;
    }

    @Override
    public String getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return null;
    }
}
