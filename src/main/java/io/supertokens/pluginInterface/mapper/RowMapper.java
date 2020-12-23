package io.supertokens.pluginInterface.mapper;

import com.google.gson.JsonParser;
import io.supertokens.pluginInterface.KeyValueInfo;
import io.supertokens.pluginInterface.emailpassword.PasswordResetTokenInfo;
import io.supertokens.pluginInterface.emailpassword.UserInfo;
import io.supertokens.pluginInterface.session.SessionInfo;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class RowMapper<T> {
    public abstract T map(ResultSet rs) throws SQLException;

    public static RowMapper<UserInfo> getUserInfoMapper() {
        return UserInfoMapper.getInstance();
    }

    public static RowMapper<SessionInfo> getSessionInfoMapper() {
        return SessionInfoMapper.getInstance();
    }

    public static RowMapper<PasswordResetTokenInfo> getPasswordResetTokenInfoMapper() {
        return PasswordResetTokenInfoMapper.getInstance();
    }

    public static RowMapper<KeyValueInfo> getKeyValueInfoMapper() {
        return KeyValueInfoMapper.getInstance();
    }

    static class UserInfoMapper extends RowMapper<UserInfo> {
        static final UserInfoMapper INSTANCE = new UserInfoMapper();
        public static UserInfoMapper getInstance() {
            return INSTANCE;
        }

        private UserInfoMapper() {}

        @Override
        public UserInfo map(ResultSet result) throws SQLException{
            return new UserInfo(result.getString("user_id"), result.getString("email"),
                    result.getString("password_hash"),
                    result.getLong("time_joined"));
        }
    }

    static class PasswordResetTokenInfoMapper extends RowMapper<PasswordResetTokenInfo> {
        static final PasswordResetTokenInfoMapper INSTANCE = new PasswordResetTokenInfoMapper();

        public static PasswordResetTokenInfoMapper getInstance(){
            return INSTANCE;
        }

        private PasswordResetTokenInfoMapper() {}

        @Override
        public PasswordResetTokenInfo map(ResultSet result) throws SQLException{
            return new PasswordResetTokenInfo(result.getString("user_id"),
                    result.getString("token"),
                    result.getLong("token_expiry"));
        }
    }

    static class SessionInfoMapper extends RowMapper<SessionInfo> {
        static final SessionInfoMapper INSTANCE = new SessionInfoMapper();

        public static SessionInfoMapper getInstance() {
            return INSTANCE;
        }

        private SessionInfoMapper() {}

        @Override
        public SessionInfo map(ResultSet result) throws SQLException{
            JsonParser jp = new JsonParser();
            return new SessionInfo(result.getString("session_handle"), result.getString("user_id"),
                    result.getString("refresh_token_hash_2"),
                    jp.parse(result.getString("session_data")).getAsJsonObject(),
                    result.getLong("expires_at"),
                    jp.parse(result.getString("jwt_user_payload")).getAsJsonObject(),
                    result.getLong("created_at_time"));
        }
    }

    static class KeyValueInfoMapper extends RowMapper<KeyValueInfo> {
        static final KeyValueInfoMapper INSTANCE = new KeyValueInfoMapper();

        public static KeyValueInfoMapper getInstance(){
            return INSTANCE;
        }

        private KeyValueInfoMapper(){
        }

        @Override
        public KeyValueInfo map(ResultSet result) throws SQLException{
            JsonParser jp = new JsonParser();
            return new KeyValueInfo(result.getString("value"), result.getLong("created_at_time"));
        }
    }
}
