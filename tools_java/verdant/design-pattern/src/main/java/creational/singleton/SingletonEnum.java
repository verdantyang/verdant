package creational.singleton;

/**
 * 单例模式（枚举）
 *
 * @author verdant
 * @since 2016/07/27
 */
public enum SingletonEnum {
    INSTANCE(3);

    private Integer point;

    SingletonEnum(Integer point) {
        this.point = point;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }
}
