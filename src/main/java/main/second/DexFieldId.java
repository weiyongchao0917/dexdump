package main.second;



public class DexFieldId {
    private Integer classId;  /* index into typeIds list for defining class */
    private Integer typeId; /* index into typeIds for field type */
    private Integer nameId;  /* index into stringIds for field name */

    public DexFieldId(Integer classId, Integer typeId, Integer nameId) {
        this.classId = classId;
        this.typeId = typeId;
        this.nameId = nameId;
    }

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Integer getNameId() {
        return nameId;
    }

    public void setNameId(Integer nameId) {
        this.nameId = nameId;
    }

    @Override
    public String toString() {
        return "DexFieldId{" +
                "classId=" + classId +
                ", typeId=" + typeId +
                ", nameId=" + nameId +
                '}';
    }
}
