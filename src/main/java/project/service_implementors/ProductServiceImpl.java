package project.service_implementors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.dto.responses.GetProductByIdResponse;
import project.dto.responses.GetProductsResponse;
import project.models.Product;
import project.repositories.ProductDAO;
import project.services.ProductService;

import javax.xml.validation.Validator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductDAO productDAO;
    private final Validator validator;

    @Override
    public GetProductsResponse getProducts() {
        List<Product> products = productDAO.getAllAvailableProducts();
        return GetProductsResponse.builder()
                .message("Success")
                .products(products)
                .build();
    }

    @Override
    public GetProductByIdResponse getProductById(int id) {
        return null;
    }
}
