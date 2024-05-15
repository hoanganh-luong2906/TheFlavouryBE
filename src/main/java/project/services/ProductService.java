package project.services;

import project.dto.responses.GetProductByIdResponse;
import project.dto.responses.GetProductsResponse;

public interface ProductService {
    GetProductsResponse getProducts();

    GetProductByIdResponse getProductById(int id);
}
