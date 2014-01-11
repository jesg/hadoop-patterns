/**
 * Autogenerated by Avro
 * 
 * DO NOT EDIT DIRECTLY
 */
package jesg.avro;  
@SuppressWarnings("all")
/** Document with a specific key. */
@org.apache.avro.specific.AvroGenerated
public class Payload extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"Payload\",\"namespace\":\"jesg.avro\",\"doc\":\"Document with a specific key.\",\"fields\":[{\"name\":\"id\",\"type\":\"int\"},{\"name\":\"tf\",\"type\":\"int\"}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }
   private int id;
   private int tf;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use {@link \#newBuilder()}. 
   */
  public Payload() {}

  /**
   * All-args constructor.
   */
  public Payload(java.lang.Integer id, java.lang.Integer tf) {
    this.id = id;
    this.tf = tf;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call. 
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return id;
    case 1: return tf;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }
  // Used by DatumReader.  Applications should not call. 
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: id = (java.lang.Integer)value$; break;
    case 1: tf = (java.lang.Integer)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'id' field.
   */
  public java.lang.Integer getId() {
    return id;
  }

  /**
   * Sets the value of the 'id' field.
   * @param value the value to set.
   */
  public void setId(java.lang.Integer value) {
    this.id = value;
  }

  /**
   * Gets the value of the 'tf' field.
   */
  public java.lang.Integer getTf() {
    return tf;
  }

  /**
   * Sets the value of the 'tf' field.
   * @param value the value to set.
   */
  public void setTf(java.lang.Integer value) {
    this.tf = value;
  }

  /** Creates a new Payload RecordBuilder */
  public static jesg.avro.Payload.Builder newBuilder() {
    return new jesg.avro.Payload.Builder();
  }
  
  /** Creates a new Payload RecordBuilder by copying an existing Builder */
  public static jesg.avro.Payload.Builder newBuilder(jesg.avro.Payload.Builder other) {
    return new jesg.avro.Payload.Builder(other);
  }
  
  /** Creates a new Payload RecordBuilder by copying an existing Payload instance */
  public static jesg.avro.Payload.Builder newBuilder(jesg.avro.Payload other) {
    return new jesg.avro.Payload.Builder(other);
  }
  
  /**
   * RecordBuilder for Payload instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<Payload>
    implements org.apache.avro.data.RecordBuilder<Payload> {

    private int id;
    private int tf;

    /** Creates a new Builder */
    private Builder() {
      super(jesg.avro.Payload.SCHEMA$);
    }
    
    /** Creates a Builder by copying an existing Builder */
    private Builder(jesg.avro.Payload.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.id)) {
        this.id = data().deepCopy(fields()[0].schema(), other.id);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.tf)) {
        this.tf = data().deepCopy(fields()[1].schema(), other.tf);
        fieldSetFlags()[1] = true;
      }
    }
    
    /** Creates a Builder by copying an existing Payload instance */
    private Builder(jesg.avro.Payload other) {
            super(jesg.avro.Payload.SCHEMA$);
      if (isValidValue(fields()[0], other.id)) {
        this.id = data().deepCopy(fields()[0].schema(), other.id);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.tf)) {
        this.tf = data().deepCopy(fields()[1].schema(), other.tf);
        fieldSetFlags()[1] = true;
      }
    }

    /** Gets the value of the 'id' field */
    public java.lang.Integer getId() {
      return id;
    }
    
    /** Sets the value of the 'id' field */
    public jesg.avro.Payload.Builder setId(int value) {
      validate(fields()[0], value);
      this.id = value;
      fieldSetFlags()[0] = true;
      return this; 
    }
    
    /** Checks whether the 'id' field has been set */
    public boolean hasId() {
      return fieldSetFlags()[0];
    }
    
    /** Clears the value of the 'id' field */
    public jesg.avro.Payload.Builder clearId() {
      fieldSetFlags()[0] = false;
      return this;
    }

    /** Gets the value of the 'tf' field */
    public java.lang.Integer getTf() {
      return tf;
    }
    
    /** Sets the value of the 'tf' field */
    public jesg.avro.Payload.Builder setTf(int value) {
      validate(fields()[1], value);
      this.tf = value;
      fieldSetFlags()[1] = true;
      return this; 
    }
    
    /** Checks whether the 'tf' field has been set */
    public boolean hasTf() {
      return fieldSetFlags()[1];
    }
    
    /** Clears the value of the 'tf' field */
    public jesg.avro.Payload.Builder clearTf() {
      fieldSetFlags()[1] = false;
      return this;
    }

    @Override
    public Payload build() {
      try {
        Payload record = new Payload();
        record.id = fieldSetFlags()[0] ? this.id : (java.lang.Integer) defaultValue(fields()[0]);
        record.tf = fieldSetFlags()[1] ? this.tf : (java.lang.Integer) defaultValue(fields()[1]);
        return record;
      } catch (Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }
}
