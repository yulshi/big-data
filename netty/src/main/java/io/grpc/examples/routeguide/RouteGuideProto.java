// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: route_guide.proto

package io.grpc.examples.routeguide;

public final class RouteGuideProto {
  private RouteGuideProto() {
  }

  public static void registerAllExtensions(
          com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
          com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
            (com.google.protobuf.ExtensionRegistryLite) registry);
  }

  static final com.google.protobuf.Descriptors.Descriptor
          internal_static_routeguide_Point_descriptor;
  static final
  com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internal_static_routeguide_Point_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
          internal_static_routeguide_Rectangle_descriptor;
  static final
  com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internal_static_routeguide_Rectangle_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
          internal_static_routeguide_Feature_descriptor;
  static final
  com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internal_static_routeguide_Feature_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
          internal_static_routeguide_FeatureDatabase_descriptor;
  static final
  com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internal_static_routeguide_FeatureDatabase_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
          internal_static_routeguide_RouteNote_descriptor;
  static final
  com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internal_static_routeguide_RouteNote_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
          internal_static_routeguide_RouteSummary_descriptor;
  static final
  com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internal_static_routeguide_RouteSummary_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
  getDescriptor() {
    return descriptor;
  }

  private static com.google.protobuf.Descriptors.FileDescriptor
          descriptor;

  static {
    String[] descriptorData = {
            "\n\021route_guide.proto\022\nrouteguide\",\n\005Point" +
                    "\022\020\n\010latitude\030\001 \001(\005\022\021\n\tlongitude\030\002 \001(\005\"I\n" +
                    "\tRectangle\022\035\n\002lo\030\001 \001(\0132\021.routeguide.Poin" +
                    "t\022\035\n\002hi\030\002 \001(\0132\021.routeguide.Point\"<\n\007Feat" +
                    "ure\022\014\n\004name\030\001 \001(\t\022#\n\010location\030\002 \001(\0132\021.ro" +
                    "uteguide.Point\"7\n\017FeatureDatabase\022$\n\007fea" +
                    "ture\030\001 \003(\0132\023.routeguide.Feature\"A\n\tRoute" +
                    "Note\022#\n\010location\030\001 \001(\0132\021.routeguide.Poin" +
                    "t\022\017\n\007message\030\002 \001(\t\"b\n\014RouteSummary\022\023\n\013po" +
                    "int_count\030\001 \001(\005\022\025\n\rfeature_count\030\002 \001(\005\022\020" +
                    "\n\010distance\030\003 \001(\005\022\024\n\014elapsed_time\030\004 \001(\0052\205" +
                    "\002\n\nRouteGuide\0226\n\nGetFeature\022\021.routeguide" +
                    ".Point\032\023.routeguide.Feature\"\000\022>\n\014ListFea" +
                    "tures\022\025.routeguide.Rectangle\032\023.routeguid" +
                    "e.Feature\"\0000\001\022>\n\013RecordRoute\022\021.routeguid" +
                    "e.Point\032\030.routeguide.RouteSummary\"\000(\001\022?\n" +
                    "\tRouteChat\022\025.routeguide.RouteNote\032\025.rout" +
                    "eguide.RouteNote\"\000(\0010\001B6\n\033io.grpc.exampl" +
                    "es.routeguideB\017RouteGuideProtoP\001\242\002\003RTGb\006" +
                    "proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
            .internalBuildGeneratedFileFrom(descriptorData,
                    new com.google.protobuf.Descriptors.FileDescriptor[]{
                    });
    internal_static_routeguide_Point_descriptor =
            getDescriptor().getMessageTypes().get(0);
    internal_static_routeguide_Point_fieldAccessorTable = new
            com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_routeguide_Point_descriptor,
            new String[]{"Latitude", "Longitude",});
    internal_static_routeguide_Rectangle_descriptor =
            getDescriptor().getMessageTypes().get(1);
    internal_static_routeguide_Rectangle_fieldAccessorTable = new
            com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_routeguide_Rectangle_descriptor,
            new String[]{"Lo", "Hi",});
    internal_static_routeguide_Feature_descriptor =
            getDescriptor().getMessageTypes().get(2);
    internal_static_routeguide_Feature_fieldAccessorTable = new
            com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_routeguide_Feature_descriptor,
            new String[]{"Name", "Location",});
    internal_static_routeguide_FeatureDatabase_descriptor =
            getDescriptor().getMessageTypes().get(3);
    internal_static_routeguide_FeatureDatabase_fieldAccessorTable = new
            com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_routeguide_FeatureDatabase_descriptor,
            new String[]{"Feature",});
    internal_static_routeguide_RouteNote_descriptor =
            getDescriptor().getMessageTypes().get(4);
    internal_static_routeguide_RouteNote_fieldAccessorTable = new
            com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_routeguide_RouteNote_descriptor,
            new String[]{"Location", "Message",});
    internal_static_routeguide_RouteSummary_descriptor =
            getDescriptor().getMessageTypes().get(5);
    internal_static_routeguide_RouteSummary_fieldAccessorTable = new
            com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_routeguide_RouteSummary_descriptor,
            new String[]{"PointCount", "FeatureCount", "Distance", "ElapsedTime",});
  }

  // @@protoc_insertion_point(outer_class_scope)
}
