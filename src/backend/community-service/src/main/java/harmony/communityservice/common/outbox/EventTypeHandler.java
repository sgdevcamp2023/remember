package harmony.communityservice.common.outbox;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

@MappedTypes(EventType.class)
public class EventTypeHandler extends BaseTypeHandler<EventType> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, EventType parameter, JdbcType jdbcType)
            throws SQLException {
        ps.setString(i, parameter.getType());
    }

    @Override
    public EventType getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String type = rs.getString(columnName);
        return type == null ? null : EventType.valueOf(type);
    }

    @Override
    public EventType getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String type = rs.getString(columnIndex);
        return type == null ? null : EventType.valueOf(type);
    }

    @Override
    public EventType getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String type = cs.getString(columnIndex);
        return type == null ? null : EventType.valueOf(type);
    }
}
