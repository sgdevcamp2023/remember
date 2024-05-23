package harmony.communityservice.common.domainentity;

public abstract class EntityLongTypeIdentifier extends EntityValueObject<EntityLongTypeIdentifier> {

    private Long id;

    public EntityLongTypeIdentifier(Long id) {
        this.id = id;
    }

    public Long longValue() {
        return id;
    }

    public Long nextValue() {
        return id + 1;
    }

    @Override
    protected Object[] getEqualityFields() {
        return new Object[] { id };
    }
}
