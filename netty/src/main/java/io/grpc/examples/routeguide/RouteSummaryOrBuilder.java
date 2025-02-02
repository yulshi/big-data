// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: route_guide.proto

package io.grpc.examples.routeguide;

public interface RouteSummaryOrBuilder extends
        // @@protoc_insertion_point(interface_extends:routeguide.RouteSummary)
        com.google.protobuf.MessageOrBuilder {

  /**
   * <pre>
   * The number of points received.
   * </pre>
   *
   * <code>int32 point_count = 1;</code>
   * @return The pointCount.
   */
  int getPointCount();

  /**
   * <pre>
   * The number of known features passed while traversing the route.
   * </pre>
   *
   * <code>int32 feature_count = 2;</code>
   * @return The featureCount.
   */
  int getFeatureCount();

  /**
   * <pre>
   * The distance covered in metres.
   * </pre>
   *
   * <code>int32 distance = 3;</code>
   * @return The distance.
   */
  int getDistance();

  /**
   * <pre>
   * The duration of the traversal in seconds.
   * </pre>
   *
   * <code>int32 elapsed_time = 4;</code>
   * @return The elapsedTime.
   */
  int getElapsedTime();
}
