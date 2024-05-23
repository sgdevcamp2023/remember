package harmony.communityservice.common.domainentity;

import java.sql.Types;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.AbstractClassJavaType;
import org.hibernate.type.descriptor.java.ImmutableMutabilityPlan;
import org.hibernate.type.descriptor.java.MutabilityPlan;
import org.hibernate.type.descriptor.jdbc.JdbcType;
import org.hibernate.type.descriptor.jdbc.JdbcTypeIndicators;

public abstract class EntityLongTypeIdentifierJavaType<T extends EntityLongTypeIdentifier> extends AbstractClassJavaType<T> {

    protected EntityLongTypeIdentifierJavaType(Class<? extends T> type) {
        super(type);
    }

    @Override
    public MutabilityPlan<T> getMutabilityPlan() {
        return ImmutableMutabilityPlan.instance();
    }

    @Override
    public JdbcType getRecommendedJdbcType(JdbcTypeIndicators indicators) {
        return indicators.getTypeConfiguration()
                .getJdbcTypeRegistry()
                .getDescriptor(Types.BIGINT);
    }

    @Override
    public String toString(T value) {
        return value.longValue().toString();
    }

    @Override
    public T fromString(CharSequence string) {
        try {
            return getJavaType().getDeclaredConstructor(Long.class).newInstance(Long.valueOf(string.toString()));
        } catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
    }

    @SuppressWarnings("unchecked")
    public <X> X unwrap(T value, Class<X> type, WrapperOptions options) {
        if (value == null) {
            return null;
        }

        if (Long.class.isAssignableFrom(type)) {
            return (X) value.longValue();
        }

        throw unknownUnwrap(type);
    }

    public <X> T wrap(X value, WrapperOptions options) {
        if (value == null) {
            return null;
        }

        if (value instanceof Long) {
            try {
                return getJavaType().getDeclaredConstructor(Long.class).newInstance(value);
            } catch (Exception ex) {
                throw new IllegalStateException(ex);
            }
        } else if (value instanceof EntityLongTypeIdentifier) {
            try {
                return getJavaType().getDeclaredConstructor(Long.class)
                        .newInstance(((EntityLongTypeIdentifier) value).longValue());
            } catch (Exception ex) {
                throw new IllegalStateException(ex);
            }
        }

        throw unknownWrap(value.getClass());
    }
}
