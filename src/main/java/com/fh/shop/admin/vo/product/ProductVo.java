package com.fh.shop.admin.vo.product;

public class ProductVo {
    private Long id;

    private String productName;

    private String price;

    private Long brandId;

    private String brandName;

    private String createDate;

    private String inputDate;

    private String modificationDate;

    private String imgFile;

    private String oldImgFile;

    private int isHot;

    private int whether;

    private String typeName;

    private Long one;

    private Long two;

    private Long three;

    public Long getOne() {
        return one;
    }

    public ProductVo setOne(Long one) {
        this.one = one;
        return this;
    }

    public Long getTwo() {
        return two;
    }

    public ProductVo setTwo(Long two) {
        this.two = two;
        return this;
    }

    public Long getThree() {
        return three;
    }

    public ProductVo setThree(Long three) {
        this.three = three;
        return this;
    }

    public String getTypeName() {
        return typeName;
    }

    public ProductVo setTypeName(String typeName) {
        this.typeName = typeName;
        return this;
    }

    public int getWhether() {
        return whether;
    }

    public ProductVo setWhether(int whether) {
        this.whether = whether;
        return this;
    }

    public int getIsHot() {
        return isHot;
    }

    public ProductVo setIsHot(int isHot) {
        this.isHot = isHot;
        return this;
    }

    public String getOldImgFile() {
        return oldImgFile;
    }

    public ProductVo setOldImgFile(String oldImgFile) {
        this.oldImgFile = oldImgFile;
        return this;
    }

    public String getImgFile() {
        return imgFile;
    }

    public ProductVo setImgFile(String imgFile) {
        this.imgFile = imgFile;
        return this;
    }

    public String getInputDate() {
        return inputDate;
    }

    public ProductVo setInputDate(String inputDate) {
        this.inputDate = inputDate;
        return this;
    }

    public String getModificationDate() {
        return modificationDate;
    }

    public ProductVo setModificationDate(String modificationDate) {
        this.modificationDate = modificationDate;
        return this;
    }

    public String getCreateDate() {
        return createDate;
    }

    public ProductVo setCreateDate(String createDate) {
        this.createDate = createDate;
        return this;
    }

    public Long getBrandId() {
        return brandId;
    }

    public ProductVo setBrandId(Long brandId) {
        this.brandId = brandId;
        return this;
    }

    public String getBrandName() {
        return brandName;
    }

    public ProductVo setBrandName(String brandName) {
        this.brandName = brandName;
        return this;
    }

    public Long getId() {
        return id;
    }

    public ProductVo setId(Long id) {
        this.id = id;
        return this;
    }

    public String getProductName() {
        return productName;
    }

    public ProductVo setProductName(String productName) {
        this.productName = productName;
        return this;
    }

    public String getPrice() {
        return price;
    }

    public ProductVo setPrice(String price) {
        this.price = price;
        return this;
    }
}
