package server.team_a.todayhouse.config;

import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import server.team_a.todayhouse.src.image.model.ProductImage;
import server.team_a.todayhouse.src.product.model.GetProductGrid;
import server.team_a.todayhouse.src.product.model.PostProductReq;
import server.team_a.todayhouse.src.product.model.Product;

@Log4j2
@Configuration
public class RootConfig {
    @Bean
    public ModelMapper getMapper(){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        addMapping(modelMapper);
        return modelMapper;
    }

    public void addMapping(ModelMapper modelMapper){
        modelMapper.typeMap(PostProductReq.class, Product.class).addMappings(mapping -> {
            mapping.skip(Product::setProductImages);
        });
        modelMapper.typeMap(PostProductReq.class, ProductImage.class).addMappings(mapping -> {
            mapping.skip(ProductImage::setProduct);
        });
    }
}
