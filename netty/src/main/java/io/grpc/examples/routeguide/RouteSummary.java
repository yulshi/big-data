// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: route_guide.proto

package io.grpc.examples.routeguide;

/**
 * <pre>
 * A RouteSummary is received in response to a RecordRoute rpc.
 * It contains the number of individual points received, the number of
 * detected features, and the total distance covered as the cumulative sum of
 * the distance between each point.
 * </pre>
 *
 * Protobuf type {@code routeguide.RouteSummary}
 */
public final class RouteSummary extends
        com.google.protobuf.GeneratedMessageV3 implements
        // @@protoc_insertion_point(message_implements:routeguide.RouteSummary)
        RouteSummaryOrBuilder {
  private static final long serialVersionUID = 0L;

  // Use RouteSummary.newBuilder() to construct.
  private RouteSummary(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }

  private RouteSummary() {
  }

  @Override
  @SuppressWarnings({"unused"})
  protected Object newInstance(
          UnusedPrivateParameter unused) {
    return new RouteSummary();
  }

  @Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }

  private RouteSummary(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    if (extensionRegistry == null) {
      throw new NullPointerException();
    }
    com.google.protobuf.UnknownFieldSet.Builder unknownFields =
            com.google.protobuf.UnknownFieldSet.newBuilder();
    try {
      boolean done = false;
      while (!done) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            done = true;
            break;
          case 8: {

            pointCount_ = input.readInt32();
            break;
          }
          case 16: {

            featureCount_ = input.readInt32();
            break;
          }
          case 24: {

            distance_ = input.readInt32();
            break;
          }
          case 32: {

            elapsedTime_ = input.readInt32();
            break;
          }
          default: {
            if (!parseUnknownField(
                    input, unknownFields, extensionRegistry, tag)) {
              done = true;
            }
            break;
          }
        }
      }
    } catch (com.google.protobuf.InvalidProtocolBufferException e) {
      throw e.setUnfinishedMessage(this);
    } catch (java.io.IOException e) {
      throw new com.google.protobuf.InvalidProtocolBufferException(
              e).setUnfinishedMessage(this);
    } finally {
      this.unknownFields = unknownFields.build();
      makeExtensionsImmutable();
    }
  }

  public static final com.google.protobuf.Descriptors.Descriptor
  getDescriptor() {
    return io.grpc.examples.routeguide.RouteGuideProto.internal_static_routeguide_RouteSummary_descriptor;
  }

  @Override
  protected FieldAccessorTable
  internalGetFieldAccessorTable() {
    return io.grpc.examples.routeguide.RouteGuideProto.internal_static_routeguide_RouteSummary_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                    RouteSummary.class, Builder.class);
  }

  public static final int POINT_COUNT_FIELD_NUMBER = 1;
  private int pointCount_;

  /**
   * <pre>
   * The number of points received.
   * </pre>
   *
   * <code>int32 point_count = 1;</code>
   * @return The pointCount.
   */
  public int getPointCount() {
    return pointCount_;
  }

  public static final int FEATURE_COUNT_FIELD_NUMBER = 2;
  private int featureCount_;

  /**
   * <pre>
   * The number of known features passed while traversing the route.
   * </pre>
   *
   * <code>int32 feature_count = 2;</code>
   * @return The featureCount.
   */
  public int getFeatureCount() {
    return featureCount_;
  }

  public static final int DISTANCE_FIELD_NUMBER = 3;
  private int distance_;

  /**
   * <pre>
   * The distance covered in metres.
   * </pre>
   *
   * <code>int32 distance = 3;</code>
   * @return The distance.
   */
  public int getDistance() {
    return distance_;
  }

  public static final int ELAPSED_TIME_FIELD_NUMBER = 4;
  private int elapsedTime_;

  /**
   * <pre>
   * The duration of the traversal in seconds.
   * </pre>
   *
   * <code>int32 elapsed_time = 4;</code>
   * @return The elapsedTime.
   */
  public int getElapsedTime() {
    return elapsedTime_;
  }

  private byte memoizedIsInitialized = -1;

  @Override
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  @Override
  public void writeTo(com.google.protobuf.CodedOutputStream output)
          throws java.io.IOException {
    if (pointCount_ != 0) {
      output.writeInt32(1, pointCount_);
    }
    if (featureCount_ != 0) {
      output.writeInt32(2, featureCount_);
    }
    if (distance_ != 0) {
      output.writeInt32(3, distance_);
    }
    if (elapsedTime_ != 0) {
      output.writeInt32(4, elapsedTime_);
    }
    unknownFields.writeTo(output);
  }

  @Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (pointCount_ != 0) {
      size += com.google.protobuf.CodedOutputStream
              .computeInt32Size(1, pointCount_);
    }
    if (featureCount_ != 0) {
      size += com.google.protobuf.CodedOutputStream
              .computeInt32Size(2, featureCount_);
    }
    if (distance_ != 0) {
      size += com.google.protobuf.CodedOutputStream
              .computeInt32Size(3, distance_);
    }
    if (elapsedTime_ != 0) {
      size += com.google.protobuf.CodedOutputStream
              .computeInt32Size(4, elapsedTime_);
    }
    size += unknownFields.getSerializedSize();
    memoizedSize = size;
    return size;
  }

  @Override
  public boolean equals(final Object obj) {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof RouteSummary)) {
      return super.equals(obj);
    }
    RouteSummary other = (RouteSummary) obj;

    if (getPointCount()
            != other.getPointCount()) return false;
    if (getFeatureCount()
            != other.getFeatureCount()) return false;
    if (getDistance()
            != other.getDistance()) return false;
    if (getElapsedTime()
            != other.getElapsedTime()) return false;
    if (!unknownFields.equals(other.unknownFields)) return false;
    return true;
  }

  @Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    hash = (37 * hash) + POINT_COUNT_FIELD_NUMBER;
    hash = (53 * hash) + getPointCount();
    hash = (37 * hash) + FEATURE_COUNT_FIELD_NUMBER;
    hash = (53 * hash) + getFeatureCount();
    hash = (37 * hash) + DISTANCE_FIELD_NUMBER;
    hash = (53 * hash) + getDistance();
    hash = (37 * hash) + ELAPSED_TIME_FIELD_NUMBER;
    hash = (53 * hash) + getElapsedTime();
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static RouteSummary parseFrom(
          java.nio.ByteBuffer data)
          throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static RouteSummary parseFrom(
          java.nio.ByteBuffer data,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static RouteSummary parseFrom(
          com.google.protobuf.ByteString data)
          throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static RouteSummary parseFrom(
          com.google.protobuf.ByteString data,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static RouteSummary parseFrom(byte[] data)
          throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static RouteSummary parseFrom(
          byte[] data,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static RouteSummary parseFrom(java.io.InputStream input)
          throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
            .parseWithIOException(PARSER, input);
  }

  public static RouteSummary parseFrom(
          java.io.InputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
            .parseWithIOException(PARSER, input, extensionRegistry);
  }

  public static RouteSummary parseDelimitedFrom(java.io.InputStream input)
          throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
            .parseDelimitedWithIOException(PARSER, input);
  }

  public static RouteSummary parseDelimitedFrom(
          java.io.InputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
            .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }

  public static RouteSummary parseFrom(
          com.google.protobuf.CodedInputStream input)
          throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
            .parseWithIOException(PARSER, input);
  }

  public static RouteSummary parseFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
            .parseWithIOException(PARSER, input, extensionRegistry);
  }

  @Override
  public Builder newBuilderForType() {
    return newBuilder();
  }

  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }

  public static Builder newBuilder(RouteSummary prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }

  @Override
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
            ? new Builder() : new Builder().mergeFrom(this);
  }

  @Override
  protected Builder newBuilderForType(
          BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }

  /**
   * <pre>
   * A RouteSummary is received in response to a RecordRoute rpc.
   * It contains the number of individual points received, the number of
   * detected features, and the total distance covered as the cumulative sum of
   * the distance between each point.
   * </pre>
   *
   * Protobuf type {@code routeguide.RouteSummary}
   */
  public static final class Builder extends
          com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
          // @@protoc_insertion_point(builder_implements:routeguide.RouteSummary)
          io.grpc.examples.routeguide.RouteSummaryOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
    getDescriptor() {
      return io.grpc.examples.routeguide.RouteGuideProto.internal_static_routeguide_RouteSummary_descriptor;
    }

    @Override
    protected FieldAccessorTable
    internalGetFieldAccessorTable() {
      return io.grpc.examples.routeguide.RouteGuideProto.internal_static_routeguide_RouteSummary_fieldAccessorTable
              .ensureFieldAccessorsInitialized(
                      RouteSummary.class, Builder.class);
    }

    // Construct using io.grpc.examples.routeguide.RouteSummary.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(
            BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }

    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3
              .alwaysUseFieldBuilders) {
      }
    }

    @Override
    public Builder clear() {
      super.clear();
      pointCount_ = 0;

      featureCount_ = 0;

      distance_ = 0;

      elapsedTime_ = 0;

      return this;
    }

    @Override
    public com.google.protobuf.Descriptors.Descriptor
    getDescriptorForType() {
      return io.grpc.examples.routeguide.RouteGuideProto.internal_static_routeguide_RouteSummary_descriptor;
    }

    @Override
    public RouteSummary getDefaultInstanceForType() {
      return RouteSummary.getDefaultInstance();
    }

    @Override
    public RouteSummary build() {
      RouteSummary result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @Override
    public RouteSummary buildPartial() {
      RouteSummary result = new RouteSummary(this);
      result.pointCount_ = pointCount_;
      result.featureCount_ = featureCount_;
      result.distance_ = distance_;
      result.elapsedTime_ = elapsedTime_;
      onBuilt();
      return result;
    }

    @Override
    public Builder clone() {
      return super.clone();
    }

    @Override
    public Builder setField(
            com.google.protobuf.Descriptors.FieldDescriptor field,
            Object value) {
      return super.setField(field, value);
    }

    @Override
    public Builder clearField(
            com.google.protobuf.Descriptors.FieldDescriptor field) {
      return super.clearField(field);
    }

    @Override
    public Builder clearOneof(
            com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return super.clearOneof(oneof);
    }

    @Override
    public Builder setRepeatedField(
            com.google.protobuf.Descriptors.FieldDescriptor field,
            int index, Object value) {
      return super.setRepeatedField(field, index, value);
    }

    @Override
    public Builder addRepeatedField(
            com.google.protobuf.Descriptors.FieldDescriptor field,
            Object value) {
      return super.addRepeatedField(field, value);
    }

    @Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof RouteSummary) {
        return mergeFrom((RouteSummary) other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(RouteSummary other) {
      if (other == RouteSummary.getDefaultInstance()) return this;
      if (other.getPointCount() != 0) {
        setPointCount(other.getPointCount());
      }
      if (other.getFeatureCount() != 0) {
        setFeatureCount(other.getFeatureCount());
      }
      if (other.getDistance() != 0) {
        setDistance(other.getDistance());
      }
      if (other.getElapsedTime() != 0) {
        setElapsedTime(other.getElapsedTime());
      }
      this.mergeUnknownFields(other.unknownFields);
      onChanged();
      return this;
    }

    @Override
    public final boolean isInitialized() {
      return true;
    }

    @Override
    public Builder mergeFrom(
            com.google.protobuf.CodedInputStream input,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws java.io.IOException {
      RouteSummary parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (RouteSummary) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private int pointCount_;

    /**
     * <pre>
     * The number of points received.
     * </pre>
     *
     * <code>int32 point_count = 1;</code>
     * @return The pointCount.
     */
    public int getPointCount() {
      return pointCount_;
    }

    /**
     * <pre>
     * The number of points received.
     * </pre>
     *
     * <code>int32 point_count = 1;</code>
     * @param value The pointCount to set.
     * @return This builder for chaining.
     */
    public Builder setPointCount(int value) {

      pointCount_ = value;
      onChanged();
      return this;
    }

    /**
     * <pre>
     * The number of points received.
     * </pre>
     *
     * <code>int32 point_count = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearPointCount() {

      pointCount_ = 0;
      onChanged();
      return this;
    }

    private int featureCount_;

    /**
     * <pre>
     * The number of known features passed while traversing the route.
     * </pre>
     *
     * <code>int32 feature_count = 2;</code>
     * @return The featureCount.
     */
    public int getFeatureCount() {
      return featureCount_;
    }

    /**
     * <pre>
     * The number of known features passed while traversing the route.
     * </pre>
     *
     * <code>int32 feature_count = 2;</code>
     * @param value The featureCount to set.
     * @return This builder for chaining.
     */
    public Builder setFeatureCount(int value) {

      featureCount_ = value;
      onChanged();
      return this;
    }

    /**
     * <pre>
     * The number of known features passed while traversing the route.
     * </pre>
     *
     * <code>int32 feature_count = 2;</code>
     * @return This builder for chaining.
     */
    public Builder clearFeatureCount() {

      featureCount_ = 0;
      onChanged();
      return this;
    }

    private int distance_;

    /**
     * <pre>
     * The distance covered in metres.
     * </pre>
     *
     * <code>int32 distance = 3;</code>
     * @return The distance.
     */
    public int getDistance() {
      return distance_;
    }

    /**
     * <pre>
     * The distance covered in metres.
     * </pre>
     *
     * <code>int32 distance = 3;</code>
     * @param value The distance to set.
     * @return This builder for chaining.
     */
    public Builder setDistance(int value) {

      distance_ = value;
      onChanged();
      return this;
    }

    /**
     * <pre>
     * The distance covered in metres.
     * </pre>
     *
     * <code>int32 distance = 3;</code>
     * @return This builder for chaining.
     */
    public Builder clearDistance() {

      distance_ = 0;
      onChanged();
      return this;
    }

    private int elapsedTime_;

    /**
     * <pre>
     * The duration of the traversal in seconds.
     * </pre>
     *
     * <code>int32 elapsed_time = 4;</code>
     * @return The elapsedTime.
     */
    public int getElapsedTime() {
      return elapsedTime_;
    }

    /**
     * <pre>
     * The duration of the traversal in seconds.
     * </pre>
     *
     * <code>int32 elapsed_time = 4;</code>
     * @param value The elapsedTime to set.
     * @return This builder for chaining.
     */
    public Builder setElapsedTime(int value) {

      elapsedTime_ = value;
      onChanged();
      return this;
    }

    /**
     * <pre>
     * The duration of the traversal in seconds.
     * </pre>
     *
     * <code>int32 elapsed_time = 4;</code>
     * @return This builder for chaining.
     */
    public Builder clearElapsedTime() {

      elapsedTime_ = 0;
      onChanged();
      return this;
    }

    @Override
    public final Builder setUnknownFields(
            final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.setUnknownFields(unknownFields);
    }

    @Override
    public final Builder mergeUnknownFields(
            final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.mergeUnknownFields(unknownFields);
    }


    // @@protoc_insertion_point(builder_scope:routeguide.RouteSummary)
  }

  // @@protoc_insertion_point(class_scope:routeguide.RouteSummary)
  private static final RouteSummary DEFAULT_INSTANCE;

  static {
    DEFAULT_INSTANCE = new RouteSummary();
  }

  public static RouteSummary getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<RouteSummary>
          PARSER = new com.google.protobuf.AbstractParser<RouteSummary>() {
    @Override
    public RouteSummary parsePartialFrom(
            com.google.protobuf.CodedInputStream input,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return new RouteSummary(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<RouteSummary> parser() {
    return PARSER;
  }

  @Override
  public com.google.protobuf.Parser<RouteSummary> getParserForType() {
    return PARSER;
  }

  @Override
  public RouteSummary getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

