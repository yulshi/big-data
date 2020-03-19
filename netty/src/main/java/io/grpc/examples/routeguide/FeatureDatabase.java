// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: route_guide.proto

package io.grpc.examples.routeguide;

/**
 * <pre>
 * Not used in the RPC.  Instead, this is here for the form serialized to disk.
 * </pre>
 *
 * Protobuf type {@code routeguide.FeatureDatabase}
 */
public  final class FeatureDatabase extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:routeguide.FeatureDatabase)
    FeatureDatabaseOrBuilder {
private static final long serialVersionUID = 0L;
  // Use FeatureDatabase.newBuilder() to construct.
  private FeatureDatabase(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private FeatureDatabase() {
    feature_ = java.util.Collections.emptyList();
  }

  @Override
  @SuppressWarnings({"unused"})
  protected Object newInstance(
      UnusedPrivateParameter unused) {
    return new FeatureDatabase();
  }

  @Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private FeatureDatabase(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    if (extensionRegistry == null) {
      throw new NullPointerException();
    }
    int mutable_bitField0_ = 0;
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
          case 10: {
            if (!((mutable_bitField0_ & 0x00000001) != 0)) {
              feature_ = new java.util.ArrayList<Feature>();
              mutable_bitField0_ |= 0x00000001;
            }
            feature_.add(
                input.readMessage(Feature.parser(), extensionRegistry));
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
      if (((mutable_bitField0_ & 0x00000001) != 0)) {
        feature_ = java.util.Collections.unmodifiableList(feature_);
      }
      this.unknownFields = unknownFields.build();
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return io.grpc.examples.routeguide.RouteGuideProto.internal_static_routeguide_FeatureDatabase_descriptor;
  }

  @Override
  protected FieldAccessorTable
      internalGetFieldAccessorTable() {
    return io.grpc.examples.routeguide.RouteGuideProto.internal_static_routeguide_FeatureDatabase_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            FeatureDatabase.class, Builder.class);
  }

  public static final int FEATURE_FIELD_NUMBER = 1;
  private java.util.List<Feature> feature_;
  /**
   * <code>repeated .routeguide.Feature feature = 1;</code>
   */
  public java.util.List<Feature> getFeatureList() {
    return feature_;
  }
  /**
   * <code>repeated .routeguide.Feature feature = 1;</code>
   */
  public java.util.List<? extends io.grpc.examples.routeguide.FeatureOrBuilder> 
      getFeatureOrBuilderList() {
    return feature_;
  }
  /**
   * <code>repeated .routeguide.Feature feature = 1;</code>
   */
  public int getFeatureCount() {
    return feature_.size();
  }
  /**
   * <code>repeated .routeguide.Feature feature = 1;</code>
   */
  public Feature getFeature(int index) {
    return feature_.get(index);
  }
  /**
   * <code>repeated .routeguide.Feature feature = 1;</code>
   */
  public io.grpc.examples.routeguide.FeatureOrBuilder getFeatureOrBuilder(
      int index) {
    return feature_.get(index);
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
    for (int i = 0; i < feature_.size(); i++) {
      output.writeMessage(1, feature_.get(i));
    }
    unknownFields.writeTo(output);
  }

  @Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    for (int i = 0; i < feature_.size(); i++) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(1, feature_.get(i));
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
    if (!(obj instanceof FeatureDatabase)) {
      return super.equals(obj);
    }
    FeatureDatabase other = (FeatureDatabase) obj;

    if (!getFeatureList()
        .equals(other.getFeatureList())) return false;
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
    if (getFeatureCount() > 0) {
      hash = (37 * hash) + FEATURE_FIELD_NUMBER;
      hash = (53 * hash) + getFeatureList().hashCode();
    }
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static FeatureDatabase parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static FeatureDatabase parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static FeatureDatabase parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static FeatureDatabase parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static FeatureDatabase parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static FeatureDatabase parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static FeatureDatabase parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static FeatureDatabase parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static FeatureDatabase parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static FeatureDatabase parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static FeatureDatabase parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static FeatureDatabase parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  @Override
  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(FeatureDatabase prototype) {
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
   * Not used in the RPC.  Instead, this is here for the form serialized to disk.
   * </pre>
   *
   * Protobuf type {@code routeguide.FeatureDatabase}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:routeguide.FeatureDatabase)
      io.grpc.examples.routeguide.FeatureDatabaseOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return io.grpc.examples.routeguide.RouteGuideProto.internal_static_routeguide_FeatureDatabase_descriptor;
    }

    @Override
    protected FieldAccessorTable
        internalGetFieldAccessorTable() {
      return io.grpc.examples.routeguide.RouteGuideProto.internal_static_routeguide_FeatureDatabase_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              FeatureDatabase.class, Builder.class);
    }

    // Construct using io.grpc.examples.routeguide.FeatureDatabase.newBuilder()
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
        getFeatureFieldBuilder();
      }
    }
    @Override
    public Builder clear() {
      super.clear();
      if (featureBuilder_ == null) {
        feature_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000001);
      } else {
        featureBuilder_.clear();
      }
      return this;
    }

    @Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return io.grpc.examples.routeguide.RouteGuideProto.internal_static_routeguide_FeatureDatabase_descriptor;
    }

    @Override
    public FeatureDatabase getDefaultInstanceForType() {
      return FeatureDatabase.getDefaultInstance();
    }

    @Override
    public FeatureDatabase build() {
      FeatureDatabase result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @Override
    public FeatureDatabase buildPartial() {
      FeatureDatabase result = new FeatureDatabase(this);
      int from_bitField0_ = bitField0_;
      if (featureBuilder_ == null) {
        if (((bitField0_ & 0x00000001) != 0)) {
          feature_ = java.util.Collections.unmodifiableList(feature_);
          bitField0_ = (bitField0_ & ~0x00000001);
        }
        result.feature_ = feature_;
      } else {
        result.feature_ = featureBuilder_.build();
      }
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
      if (other instanceof FeatureDatabase) {
        return mergeFrom((FeatureDatabase)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(FeatureDatabase other) {
      if (other == FeatureDatabase.getDefaultInstance()) return this;
      if (featureBuilder_ == null) {
        if (!other.feature_.isEmpty()) {
          if (feature_.isEmpty()) {
            feature_ = other.feature_;
            bitField0_ = (bitField0_ & ~0x00000001);
          } else {
            ensureFeatureIsMutable();
            feature_.addAll(other.feature_);
          }
          onChanged();
        }
      } else {
        if (!other.feature_.isEmpty()) {
          if (featureBuilder_.isEmpty()) {
            featureBuilder_.dispose();
            featureBuilder_ = null;
            feature_ = other.feature_;
            bitField0_ = (bitField0_ & ~0x00000001);
            featureBuilder_ = 
              com.google.protobuf.GeneratedMessageV3.alwaysUseFieldBuilders ?
                 getFeatureFieldBuilder() : null;
          } else {
            featureBuilder_.addAllMessages(other.feature_);
          }
        }
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
      FeatureDatabase parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (FeatureDatabase) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }
    private int bitField0_;

    private java.util.List<Feature> feature_ =
      java.util.Collections.emptyList();
    private void ensureFeatureIsMutable() {
      if (!((bitField0_ & 0x00000001) != 0)) {
        feature_ = new java.util.ArrayList<Feature>(feature_);
        bitField0_ |= 0x00000001;
       }
    }

    private com.google.protobuf.RepeatedFieldBuilderV3<
        Feature, Feature.Builder, io.grpc.examples.routeguide.FeatureOrBuilder> featureBuilder_;

    /**
     * <code>repeated .routeguide.Feature feature = 1;</code>
     */
    public java.util.List<Feature> getFeatureList() {
      if (featureBuilder_ == null) {
        return java.util.Collections.unmodifiableList(feature_);
      } else {
        return featureBuilder_.getMessageList();
      }
    }
    /**
     * <code>repeated .routeguide.Feature feature = 1;</code>
     */
    public int getFeatureCount() {
      if (featureBuilder_ == null) {
        return feature_.size();
      } else {
        return featureBuilder_.getCount();
      }
    }
    /**
     * <code>repeated .routeguide.Feature feature = 1;</code>
     */
    public Feature getFeature(int index) {
      if (featureBuilder_ == null) {
        return feature_.get(index);
      } else {
        return featureBuilder_.getMessage(index);
      }
    }
    /**
     * <code>repeated .routeguide.Feature feature = 1;</code>
     */
    public Builder setFeature(
        int index, Feature value) {
      if (featureBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureFeatureIsMutable();
        feature_.set(index, value);
        onChanged();
      } else {
        featureBuilder_.setMessage(index, value);
      }
      return this;
    }
    /**
     * <code>repeated .routeguide.Feature feature = 1;</code>
     */
    public Builder setFeature(
        int index, Feature.Builder builderForValue) {
      if (featureBuilder_ == null) {
        ensureFeatureIsMutable();
        feature_.set(index, builderForValue.build());
        onChanged();
      } else {
        featureBuilder_.setMessage(index, builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .routeguide.Feature feature = 1;</code>
     */
    public Builder addFeature(Feature value) {
      if (featureBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureFeatureIsMutable();
        feature_.add(value);
        onChanged();
      } else {
        featureBuilder_.addMessage(value);
      }
      return this;
    }
    /**
     * <code>repeated .routeguide.Feature feature = 1;</code>
     */
    public Builder addFeature(
        int index, Feature value) {
      if (featureBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureFeatureIsMutable();
        feature_.add(index, value);
        onChanged();
      } else {
        featureBuilder_.addMessage(index, value);
      }
      return this;
    }
    /**
     * <code>repeated .routeguide.Feature feature = 1;</code>
     */
    public Builder addFeature(
        Feature.Builder builderForValue) {
      if (featureBuilder_ == null) {
        ensureFeatureIsMutable();
        feature_.add(builderForValue.build());
        onChanged();
      } else {
        featureBuilder_.addMessage(builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .routeguide.Feature feature = 1;</code>
     */
    public Builder addFeature(
        int index, Feature.Builder builderForValue) {
      if (featureBuilder_ == null) {
        ensureFeatureIsMutable();
        feature_.add(index, builderForValue.build());
        onChanged();
      } else {
        featureBuilder_.addMessage(index, builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .routeguide.Feature feature = 1;</code>
     */
    public Builder addAllFeature(
        Iterable<? extends Feature> values) {
      if (featureBuilder_ == null) {
        ensureFeatureIsMutable();
        com.google.protobuf.AbstractMessageLite.Builder.addAll(
            values, feature_);
        onChanged();
      } else {
        featureBuilder_.addAllMessages(values);
      }
      return this;
    }
    /**
     * <code>repeated .routeguide.Feature feature = 1;</code>
     */
    public Builder clearFeature() {
      if (featureBuilder_ == null) {
        feature_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000001);
        onChanged();
      } else {
        featureBuilder_.clear();
      }
      return this;
    }
    /**
     * <code>repeated .routeguide.Feature feature = 1;</code>
     */
    public Builder removeFeature(int index) {
      if (featureBuilder_ == null) {
        ensureFeatureIsMutable();
        feature_.remove(index);
        onChanged();
      } else {
        featureBuilder_.remove(index);
      }
      return this;
    }
    /**
     * <code>repeated .routeguide.Feature feature = 1;</code>
     */
    public Feature.Builder getFeatureBuilder(
        int index) {
      return getFeatureFieldBuilder().getBuilder(index);
    }
    /**
     * <code>repeated .routeguide.Feature feature = 1;</code>
     */
    public io.grpc.examples.routeguide.FeatureOrBuilder getFeatureOrBuilder(
        int index) {
      if (featureBuilder_ == null) {
        return feature_.get(index);  } else {
        return featureBuilder_.getMessageOrBuilder(index);
      }
    }
    /**
     * <code>repeated .routeguide.Feature feature = 1;</code>
     */
    public java.util.List<? extends io.grpc.examples.routeguide.FeatureOrBuilder> 
         getFeatureOrBuilderList() {
      if (featureBuilder_ != null) {
        return featureBuilder_.getMessageOrBuilderList();
      } else {
        return java.util.Collections.unmodifiableList(feature_);
      }
    }
    /**
     * <code>repeated .routeguide.Feature feature = 1;</code>
     */
    public Feature.Builder addFeatureBuilder() {
      return getFeatureFieldBuilder().addBuilder(
          Feature.getDefaultInstance());
    }
    /**
     * <code>repeated .routeguide.Feature feature = 1;</code>
     */
    public Feature.Builder addFeatureBuilder(
        int index) {
      return getFeatureFieldBuilder().addBuilder(
          index, Feature.getDefaultInstance());
    }
    /**
     * <code>repeated .routeguide.Feature feature = 1;</code>
     */
    public java.util.List<Feature.Builder>
         getFeatureBuilderList() {
      return getFeatureFieldBuilder().getBuilderList();
    }
    private com.google.protobuf.RepeatedFieldBuilderV3<
        Feature, Feature.Builder, io.grpc.examples.routeguide.FeatureOrBuilder>
        getFeatureFieldBuilder() {
      if (featureBuilder_ == null) {
        featureBuilder_ = new com.google.protobuf.RepeatedFieldBuilderV3<
            Feature, Feature.Builder, io.grpc.examples.routeguide.FeatureOrBuilder>(
                feature_,
                ((bitField0_ & 0x00000001) != 0),
                getParentForChildren(),
                isClean());
        feature_ = null;
      }
      return featureBuilder_;
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


    // @@protoc_insertion_point(builder_scope:routeguide.FeatureDatabase)
  }

  // @@protoc_insertion_point(class_scope:routeguide.FeatureDatabase)
  private static final FeatureDatabase DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new FeatureDatabase();
  }

  public static FeatureDatabase getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<FeatureDatabase>
      PARSER = new com.google.protobuf.AbstractParser<FeatureDatabase>() {
    @Override
    public FeatureDatabase parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new FeatureDatabase(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<FeatureDatabase> parser() {
    return PARSER;
  }

  @Override
  public com.google.protobuf.Parser<FeatureDatabase> getParserForType() {
    return PARSER;
  }

  @Override
  public FeatureDatabase getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

