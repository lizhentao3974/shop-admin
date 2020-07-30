package com.fh.shop.admin.controller.product;

import com.fh.shop.admin.annotation.LogAnnotation;
import com.fh.shop.admin.biz.brand.IBrandService;
import com.fh.shop.admin.biz.product.IProductService;
import com.fh.shop.admin.common.ServerResponse;
import com.fh.shop.admin.common.SystemConstant;
import com.fh.shop.admin.param.product.ProductSearchParam;
import com.fh.shop.admin.po.brand.Brand;
import com.fh.shop.admin.po.product.Product;
import com.fh.shop.admin.util.DateUtil;
import com.fh.shop.admin.util.ExcelUtils;
import com.fh.shop.admin.util.FileUtil;
import com.fh.shop.admin.vo.product.ProductVo;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.util.*;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Resource(name = "productService")
    private IProductService productService;

    @Resource(name = "brandService")
    private IBrandService brandService;

    @RequestMapping("/toAdd")
    public String toAdd() {
        return "product/add";
    }

    @RequestMapping("/toList")
    public String toList() {
        return "product/list";
    }

    @RequestMapping("/toUpdate")
    public String toUpdate() {
        return "product/updateProduct";
    }

    @RequestMapping("/addProduct")
    @ResponseBody
    @LogAnnotation(info = "添加商品")
    public ServerResponse addProduct(Product product) {
        Date date = new Date();
        product.setInputDate(date);
        product.setModificationDate(date);

        productService.addProduct(product);

        return productService.addProduct(product);
    }

    @RequestMapping("/queryProductList")
    @ResponseBody
    public Map queryProductList(ProductSearchParam productSearchParam) {
        return productService.queryProduct(productSearchParam);
    }

    @RequestMapping("/deleteProduct")
    @ResponseBody
    @LogAnnotation(info = "删除商品")
    public ServerResponse deleteProduct(Long id, HttpServletRequest request) {
        Product product = productService.editProduct(id);
        if (StringUtils.isNotEmpty(product.getImgFile())) {
            String imgFile = product.getImgFile();
            String realPath = request.getServletContext().getRealPath(imgFile);
            File file = new File(realPath);
            if (file.exists()) {
                file.delete();
            }
        }
        return productService.deleteProduct(id);
    }

    @RequestMapping("/editProduct")
    @ResponseBody
    public Map editProduct(Long id) {
        Product product = productService.editProduct(id);

        ProductVo productVo = new ProductVo();
        productVo.setId(product.getId());
        productVo.setProductName(product.getProductName());
        productVo.setPrice(product.getPrice().toString());
        productVo.setBrandId(product.getBrandId());

        productVo.setCreateDate(DateUtil.date2str(product.getCreateDate(), DateUtil.Y_M_D));
        productVo.setImgFile(product.getImgFile());
        productVo.setOldImgFile(product.getOldImgFile());
        productVo.setTypeName(product.getOneTypeName() + "-->" + product.getTwoTypeName() + "-->" + product.getThreeTypeName());
        productVo.setOne(product.getOne());
        productVo.setTwo(product.getTwo());
        productVo.setThree(product.getThree());

        Map result = new HashMap();
        result.put("code", 200);
        result.put("data", productVo);
        result.put("msg", "ok");
        return result;
    }

    @RequestMapping("/updateStatus")
    @ResponseBody
    @LogAnnotation(info = "修改商品是否热销")
    public ServerResponse updateStatus(Long id, Integer stauts) {
        return productService.updateStatus(id, stauts);
    }

    @RequestMapping("/updateProduct")
    @ResponseBody
    @LogAnnotation(info = "修改商品信息")
    public ServerResponse updateProduct(Product product, HttpServletRequest request) {
        if (StringUtils.isNotEmpty(product.getImgFile())) {
            if (StringUtils.isNotEmpty(product.getOldImgFile())) {
                String oldImgFile = product.getOldImgFile();
                String realPath = request.getServletContext().getRealPath(oldImgFile);
                File file = new File(realPath);
                if (file.exists()) {
                    file.delete();
                }
            }
        } else {
            product.setImgFile(product.getOldImgFile());
        }
        product.setModificationDate(new Date());
        productService.updateProduct(product);
        return productService.updateProduct(product);
    }

    @RequestMapping("/deleteAll")
    @ResponseBody
    @LogAnnotation(info = "批量删除商品")
    public ServerResponse deleteAll(@RequestParam("ids[]") Long[] ids) {
        return productService.deleteAll(ids);
    }

    @RequestMapping("/emportPdf")
    @LogAnnotation(info = "商品导出pdf")
    public void emportPdf(ProductSearchParam productSearchParam, HttpServletResponse response) {
        List<Product> productList = productService.findProduct(productSearchParam);


        Configuration configuration = new Configuration();
        configuration.setDefaultEncoding("utf-8");
        configuration.setClassForTemplateLoading(this.getClass(), SystemConstant.TEMP_PATH);
        try {
            Template template = configuration.getTemplate(SystemConstant.PRODUCT_PDF_HTML);
            Map result = new HashMap();

            result.put("product", productList);

            StringWriter sw = new StringWriter();

            template.process(result, sw);

            FileUtil.pdfDownloadFile(response, sw.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/emportExcel")
    @LogAnnotation(info = "商品导出Excel")
    public void emportExcel(ProductSearchParam productSearchParam, HttpServletResponse response) {
        List<Product> productList = productService.findProduct(productSearchParam);

        String[] props = {"productName", "price", "brandName", "createDate"};

        String[] titles = {"商品名", "价格", "品牌名", "生产日期"};

        XSSFWorkbook workbook = ExcelUtils.titleRow(productList, props, titles, "商品列表");

        FileUtil.excelDownload(workbook, response);

    }


    @RequestMapping("/updateIsup")
    @ResponseBody
    @LogAnnotation(info = "修改商品上下架")
    public ServerResponse updateIsup(Long id, Integer isup) {
        return productService.updateIsup(id, isup);
    }

    @RequestMapping("/importExcel")
    @ResponseBody
    @LogAnnotation(info = "商品导入Excel")
    public ServerResponse importExcels(String filePath) {
        try {
            FileInputStream fileInputStream = new FileInputStream(filePath);
            XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);

            XSSFSheet sheetAt = workbook.getSheetAt(0);
            int lastRowNum = sheetAt.getLastRowNum();

            for (int i = 14; i <= lastRowNum; i++) {
                Product product = getProduct(sheetAt, i);

                productService.addProduct(product);
                File file = new File(filePath);
                if (file.exists()) {
                    file.delete();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ServerResponse.success();
    }

    private Product getProduct(XSSFSheet sheetAt, int i) {
        XSSFRow row = sheetAt.getRow(i);
        String productName = row.getCell(3).getStringCellValue();
        double price = row.getCell(4).getNumericCellValue();
        String brandName = row.getCell(5).getStringCellValue();
        Date createDate = row.getCell(6).getDateCellValue();

        Date date = new Date();
        Product product = new Product();
        product.setProductName(productName);
        product.setPrice(new BigDecimal(price));
        product.setCreateDate(createDate);
        product.setModificationDate(date);
        product.setInputDate(date);
        Long brandId = brandService.queryBrandIdByName(brandName);
        Brand brand = new Brand();
        if (brandId == null) {
            brand.setBrandName(brandName);
            brandService.addBrand1(brand);
            product.setBrandId(brand.getId());
        } else {
            Long id = brand.getId();
            product.setBrandId(brandId);
        }
        return product;
    }

    @RequestMapping("/exportWord")
    @LogAnnotation(info = "商品导出word")
    public void exportWord(ProductSearchParam productSearchParam, HttpServletResponse response, HttpServletRequest request) {
        List<Product> productList = productService.findProduct(productSearchParam);

        List<ProductVo> productVoList = toProductVoList(productList);

        Configuration configuration = new Configuration();

        configuration.setDefaultEncoding("utf-8");

        FileOutputStream fileOutputStream = null;
        OutputStreamWriter outputStreamWriter = null;

        configuration.setClassForTemplateLoading(this.getClass(), SystemConstant.TEMP_PATH);

        try {
            Template template = configuration.getTemplate(SystemConstant.WORD_TEMP_XML);
            Map data = new HashMap();
            data.put("products", productVoList);
            String path = "f:/1906b/" + UUID.randomUUID().toString() + ".doc";
            fileOutputStream = new FileOutputStream(path);

            outputStreamWriter = new OutputStreamWriter(fileOutputStream, "utf-8");

            template.process(data, outputStreamWriter);
            FileUtil.downloadFile(request, response, new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        } finally {
            if (outputStreamWriter != null) {
                try {
                    outputStreamWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private List<ProductVo> toProductVoList(List<Product> productList) {
        List<ProductVo> productVoList = new ArrayList<>();
        for (Product product : productList) {
            ProductVo productVo = new ProductVo();
            productVo.setProductName(product.getProductName());
            productVo.setPrice(product.getPrice().toString());
            productVo.setBrandName(product.getBrandName());
            productVo.setCreateDate(DateUtil.date2str(product.getCreateDate(), DateUtil.Y_M_D));
            productVoList.add(productVo);
        }
        return productVoList;
    }

}
