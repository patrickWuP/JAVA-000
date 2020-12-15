package com.wp.bean;

import java.math.BigDecimal;
import java.util.Date;

public class Order {
    private Long id;
    
    private Long userId;
    
    private Long CommodityId;
    
    private BigDecimal CommodityPrice;
    
    private Integer state;
    
    private Date createTime;
    
    private Date modifyTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCommodityId() {
        return CommodityId;
    }

    public void setCommodityId(Long commodityId) {
        CommodityId = commodityId;
    }

    public BigDecimal getCommodityPrice() {
        return CommodityPrice;
    }

    public void setCommodityPrice(BigDecimal commodityPrice) {
        CommodityPrice = commodityPrice;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}
