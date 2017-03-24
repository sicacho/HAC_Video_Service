package com.hac.domain;



/**
 * Created by tnkhang on 3/21/2017.
 */
public class Video {
  private String interactionId;
  private String location;
  private String name;
  private int resolution;

  public String getInteractionId() {
    return interactionId;
  }

  public void setInteractionId(String interactionId) {
    this.interactionId = interactionId;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getResolution() {
    return resolution;
  }

  public void setResolution(int resolution) {
    this.resolution = resolution;
  }

  @Override
  public String toString() {
    return "Video{" +
            "interactionId='" + interactionId + '\'' +
            ", location='" + location + '\'' +
            ", name='" + name + '\'' +
            ", resolution=" + resolution +
            '}';
  }
}
