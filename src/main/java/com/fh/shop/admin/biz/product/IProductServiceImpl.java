package com.fh.shop.admin.biz.product;

import com.fh.shop.admin.common.ServerResponse;
import com.fh.shop.admin.mapper.product.IProductMapper;
import com.fh.shop.admin.param.product.ProductSearchParam;
import com.fh.shop.admin.po.product.Product;
import com.fh.shop.admin.util.DateUtil;
import com.fh.shop.admin.vo.product.ProductVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("productService")
public class IProductServiceImpl implements IProductService {

    @Autowired
    private IProductMapper productMapper;

    public ServerResponse addProduct(Product product) {
        productMapper.insert(product);
        return ServerResponse.success();
    }

    @Override
    public Map queryProduct(ProductSearchParam productSearchParam) {
        Long count = productMapper.queryCount(productSearchParam);

        List<Product> products = productMapper.queryProduct(productSearchParam);

        List<ProductVo> productVoList = new ArrayList<>();
        for (Product product : products) {
            ProductVo productVo = new ProductVo();
            productVo.setId(product.getId());
            productVo.setProductName(product.getProductName());
            productVo.setPrice(product.getPrice().toString());
            productVo.setBrandName(product.getBrandName());
            productVo.setCreateDate(DateUtil.date2str(product.getCreateDate(), DateUtil.Y_M_D));
            productVo.setInputDate(DateUtil.date2str(product.getInputDate(), DateUtil.FULL_TIME));
            productVo.setModificationDate(DateUtil.date2str(product.getModificationDate(), DateUtil.FULL_TIME));
            productVo.setImgFile(product.getImgFile());
            productVo.setIsHot(product.getIsHot());
            productVo.setWhether(product.getWhether());
            productVo.setTypeName(product.getOneTypeName() + "-->" + product.getTwoTypeName() + "-->" + product.getThreeTypeName());
            productVoList.add(productVo);
        }

        Map result = new HashMap();
        result.put("draw", productSearchParam.getDraw());
        result.put("data", productVoList);
        result.put("recordsFiltered", count);
        result.put("recordsTotal", count);
        return result;
    }

    @Override
    public ServerResponse deleteProduct(Long id) {
        productMapper.deleteById(id);
        return ServerResponse.success();
    }

    @Override
    public Product editProduct(Long id) {
        Product product = productMapper.editProduct(id);
        return product;
    }

    @Override
    public ServerResponse updateProduct(Product product) {
        productMapper.updateById(product);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse deleteAll(Long[] ids) {
        List<Long> longs = Arrays.asList(ids);
        productMapper.deleteBatchIds(longs);
        return ServerResponse.success();
    }

    @Override
    public List<Product> findProduct(ProductSearchParam productSearchParam) {
        List<Product> productList = productMapper.findProduct(productSearchParam);
        return productList;
    }

    @Override
    public ServerResponse updateStatus(Long id, Integer stauts) {
        Product product = new Product();
        product.setId(id);
        product.setIsHot(stauts);
        productMapper.updateById(product);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse updateIsup(Long id, Integer isup) {
        Product product = new Product();
        product.setWhether(isup);
        product.setId(id);
        productMapper.updateById(product);
        return ServerResponse.success();
    }

}
