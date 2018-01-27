package com.mvp.lt.flyset.blue.bean;

/**
 * $activityName
 *
 * @author ${LiuTao}
 * @date 2018/1/27/027
 */

public class LaserData {
    private double levationAngle;//仰角
    private double slopeDistance;//斜距
    private double sineHigh;//正弦高
    private double horizontalDistance;//水平距离
    private double twoHight;//两点高
    private double guideCorner;//指南角/方位角
    private double horizontalAngle;//水平夹角
    private double spanDis;//跨距

    public double getLevationAngle() {
        return levationAngle;
    }

    public void setLevationAngle(double levationAngle) {
        this.levationAngle = levationAngle;
    }

    public double getSlopeDistance() {
        return slopeDistance;
    }

    public void setSlopeDistance(double slopeDistance) {
        this.slopeDistance = slopeDistance;
    }

    public double getSineHigh() {
        return sineHigh;
    }

    public void setSineHigh(double sineHigh) {
        this.sineHigh = sineHigh;
    }

    public double getHorizontalDistance() {
        return horizontalDistance;
    }

    public void setHorizontalDistance(double horizontalDistance) {
        this.horizontalDistance = horizontalDistance;
    }

    public double getTwoHight() {
        return twoHight;
    }

    public void setTwoHight(double twoHight) {
        this.twoHight = twoHight;
    }

    public double getGuideCorner() {
        return guideCorner;
    }

    public void setGuideCorner(double guideCorner) {
        this.guideCorner = guideCorner;
    }

    public double getHorizontalAngle() {
        return horizontalAngle;
    }

    public void setHorizontalAngle(double horizontalAngle) {
        this.horizontalAngle = horizontalAngle;
    }

    public double getSpanDis() {
        return spanDis;
    }

    public void setSpanDis(double spanDis) {
        this.spanDis = spanDis;
    }

    @Override
    public String toString() {
        return "LaserData{" +
                "levationAngle=" + levationAngle +
                ", slopeDistance=" + slopeDistance +
                ", sineHigh=" + sineHigh +
                ", horizontalDistance=" + horizontalDistance +
                ", twoHight=" + twoHight +
                ", guideCorner=" + guideCorner +
                ", horizontalAngle=" + horizontalAngle +
                ", spanDis=" + spanDis +
                '}';
    }
}
