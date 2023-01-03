package com.abasecode.opencode.base.safe.desensitize.handler;

import com.abasecode.opencode.base.safe.config.SafeConfig;
import com.abasecode.opencode.base.safe.desensitize.DataSecret;
import com.abasecode.opencode.base.safe.util.CodeAesUtils;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.springframework.beans.factory.annotation.Autowired;
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
@MappedTypes(DataSecret.class)
@Slf4j
@Component
public class DataSecretHandler extends BaseTypeHandler<String> {
    @Autowired
    SafeConfig.CodeSafe codeSafe;
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, String s, JdbcType jdbcType) throws SQLException {
        try{
            String s1 = CodeAesUtils.encrypt(s, codeSafe.getKey(),codeSafe.getIv());
            preparedStatement.setString(i,s1);
//            log.info("**********************");
//            log.info("原值："+ s);
//            log.info("加密："+s1);
//            log.info("**********************");
        }catch (Exception e){
            preparedStatement.setString(i,s);
        }
    }

    @Override
    public String getNullableResult(ResultSet resultSet, String s) throws SQLException {
        String s1 = resultSet.getString(s);
        try{
//            log.info("**********************");
//            log.info("字段："+ s);
//            log.info("原值："+ s1);
            s1= CodeAesUtils.decrypt(s1,codeSafe.getKey(),codeSafe.getIv());
//            log.info("解密："+s1);
//            log.info("**********************");
        } catch (Exception e){
        }
        return s1;
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
