package harmony.communityservice.common.outbox;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import java.sql.*;

public class EnumTypeHandler<T extends Enum<T>> extends BaseTypeHandler<T> {
    private final Class<T> type;

    public EnumTypeHandler(Class<T> type) {
        if (type == null) {
            throw new IllegalArgumentException();
        }
        this.type = type;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.name());
    }

    @Override
    public T getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String value = rs.getString(columnName);
        return (value == null) ? null : Enum.valueOf(type, value);
    }

    @Override
    public T getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String value = rs.getString(columnIndex);
        return (value == null) ? null : Enum.valueOf(type, value);
    }

    @Override
    public T getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String value = cs.getString(columnIndex);
        return (value == null) ? null : Enum.valueOf(type, value);
    }
}

