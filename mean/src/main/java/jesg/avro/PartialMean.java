package jesg.avro;
/**
 * Autogenerated by Avro
 * 
 * DO NOT EDIT DIRECTLY
 */
@SuppressWarnings("all")
/** Partial result for calculating the mean. */
@org.apache.avro.specific.AvroGenerated
public class PartialMean extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"PartialMean\",\"doc\":\"Partial result for calculating the mean.\",\"fields\":[{\"name\":\"sum\",\"type\":\"long\",\"ordering\":\"ignore\"},{\"name\":\"count\",\"type\":\"long\",\"ordering\":\"ascending\"}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }
   private long sum;
   private long count;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use {@link \#newBuilder()}. 
   */
  public PartialMean() {}

  /**
   * All-args constructor.
   */
  public PartialMean(java.lang.Long sum, java.lang.Long count) {
    this.sum = sum;
    this.count = count;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call. 
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return sum;
    case 1: return count;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }
  // Used by DatumReader.  Applications should not call. 
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: sum = (java.lang.Long)value$; break;
    case 1: count = (java.lang.Long)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'sum' field.
   */
  public java.lang.Long getSum() {
    return sum;
  }

  /**
   * Sets the value of the 'sum' field.
   * @param value the value to set.
   */
  public void setSum(java.lang.Long value) {
    this.sum = value;
  }

  /**
   * Gets the value of the 'count' field.
   */
  public java.lang.Long getCount() {
    return count;
  }

  /**
   * Sets the value of the 'count' field.
   * @param value the value to set.
   */
  public void setCount(java.lang.Long value) {
    this.count = value;
  }

  /** Creates a new PartialMean RecordBuilder */
  public static PartialMean.Builder newBuilder() {
    return new PartialMean.Builder();
  }
  
  /** Creates a new PartialMean RecordBuilder by copying an existing Builder */
  public static PartialMean.Builder newBuilder(PartialMean.Builder other) {
    return new PartialMean.Builder(other);
  }
  
  /** Creates a new PartialMean RecordBuilder by copying an existing PartialMean instance */
  public static PartialMean.Builder newBuilder(PartialMean other) {
    return new PartialMean.Builder(other);
  }
  
  /**
   * RecordBuilder for PartialMean instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<PartialMean>
    implements org.apache.avro.data.RecordBuilder<PartialMean> {

    private long sum;
    private long count;

    /** Creates a new Builder */
    private Builder() {
      super(PartialMean.SCHEMA$);
    }
    
    /** Creates a Builder by copying an existing Builder */
    private Builder(PartialMean.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.sum)) {
        this.sum = data().deepCopy(fields()[0].schema(), other.sum);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.count)) {
        this.count = data().deepCopy(fields()[1].schema(), other.count);
        fieldSetFlags()[1] = true;
      }
    }
    
    /** Creates a Builder by copying an existing PartialMean instance */
    private Builder(PartialMean other) {
            super(PartialMean.SCHEMA$);
      if (isValidValue(fields()[0], other.sum)) {
        this.sum = data().deepCopy(fields()[0].schema(), other.sum);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.count)) {
        this.count = data().deepCopy(fields()[1].schema(), other.count);
        fieldSetFlags()[1] = true;
      }
    }

    /** Gets the value of the 'sum' field */
    public java.lang.Long getSum() {
      return sum;
    }
    
    /** Sets the value of the 'sum' field */
    public PartialMean.Builder setSum(long value) {
      validate(fields()[0], value);
      this.sum = value;
      fieldSetFlags()[0] = true;
      return this; 
    }
    
    /** Checks whether the 'sum' field has been set */
    public boolean hasSum() {
      return fieldSetFlags()[0];
    }
    
    /** Clears the value of the 'sum' field */
    public PartialMean.Builder clearSum() {
      fieldSetFlags()[0] = false;
      return this;
    }

    /** Gets the value of the 'count' field */
    public java.lang.Long getCount() {
      return count;
    }
    
    /** Sets the value of the 'count' field */
    public PartialMean.Builder setCount(long value) {
      validate(fields()[1], value);
      this.count = value;
      fieldSetFlags()[1] = true;
      return this; 
    }
    
    /** Checks whether the 'count' field has been set */
    public boolean hasCount() {
      return fieldSetFlags()[1];
    }
    
    /** Clears the value of the 'count' field */
    public PartialMean.Builder clearCount() {
      fieldSetFlags()[1] = false;
      return this;
    }

    @Override
    public PartialMean build() {
      try {
        PartialMean record = new PartialMean();
        record.sum = fieldSetFlags()[0] ? this.sum : (java.lang.Long) defaultValue(fields()[0]);
        record.count = fieldSetFlags()[1] ? this.count : (java.lang.Long) defaultValue(fields()[1]);
        return record;
      } catch (Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }
}
