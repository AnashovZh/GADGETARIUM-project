package zhanuzak.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import zhanuzak.dto.response.BrandResponse;
import zhanuzak.models.Brand;

import java.util.List;

public interface BrandRepository extends JpaRepository<Brand, Long> {
    @Query("select new zhanuzak.dto.response.BrandResponse(b.id,b.brandName,b.image)from Brand b")
    List<BrandResponse> getAll();

    @Query("SELECT b FROM Brand b WHERE b.brandName = ?1")
    Brand findBrandByBrandName(String brandName);
}
