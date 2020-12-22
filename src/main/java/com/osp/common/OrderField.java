/**
 *
 */
package com.osp.common;

/**
 * @author HUNGHC
 *
 */
public class OrderField {

    /**
     * Field name
     */
    private String fieldName;

    /**
     * Order Type
     */
    private OrderType orderType = OrderType.ASC;

    /**
     * Order Value
     */
    private static final String ORDER_TYPE_ASC = "ASC";
    private static final String ODER_TYPE_DESC = "DESC";

    /**
     * Order Type
     */
    public enum OrderType {
        /**
         * DESC
         */
        DESC /**
         * ASC
         */
        , ASC;

        public String getValue() {
            switch (this) {
                case DESC:
                    return ODER_TYPE_DESC;
                case ASC:
                    return ORDER_TYPE_ASC;
                default:
                    return ORDER_TYPE_ASC;
            }
        }

        public static OrderType changeValue(String value) {
            if (value.equals(ODER_TYPE_DESC)) {
                return DESC;
            } else {
                return ASC;
            }
        }
    };

    /**
     * Default Constructor
     */
    public OrderField() {
        this.orderType = OrderType.ASC;
    }

    /**
     * Constructor
     *
     * @param fieldName
     */
    public OrderField(String fieldName) {
        this.fieldName = fieldName;
        this.orderType = OrderType.ASC;
    }

    /**
     * Constructor
     *
     * @param fieldName
     * @param orderType
     */
    public OrderField(String fieldName, OrderType orderType) {
        this.fieldName = fieldName;
        this.orderType = orderType;
    }

    /**
     * @return the fieldName
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * @param fieldName the fieldName to set
     */
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    /**
     * @return the orderType
     */
    public OrderType getOrderType() {
        return orderType;
    }

    /**
     * @param orderType the orderType to set
     */
    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }
}
