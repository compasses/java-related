package jet.mq.products;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * Created by I311352 on 10/9/2016.
 */
@RestController
@RequestMapping("/api/product")
public class ProductController {
    private ProductService productService;

    @Autowired
    ProductController(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/messages")
    public HashMap<String, Integer> procMessages(@RequestParam(defaultValue = "") String name) {
        return productService.getStats();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/query")
    public String doQuery(@RequestBody String body) {

        return "";
    }
}
