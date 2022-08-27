package backend.jangbogoProject.item.repository;

import backend.jangbogoProject.item.domain.Item;
import backend.jangbogoProject.item.domain.Market;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MarketRepository extends JpaRepository<Market, Integer> {
    @Query(value = "SELECT * FROM Market WHERE M_GU_CODE = ?1 ORDER BY M_NAME", nativeQuery = true)
    public List<Market> findAllByMarketsInGu(int marketGuCode);
}
