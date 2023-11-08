package cz.cvut.fel.ear.library.dao;

import cz.cvut.fel.ear.library.model.Rating;
import org.springframework.stereotype.Repository;

@Repository
public class RatingDao extends BaseDao<Rating> {
    public RatingDao() {
        super(Rating.class);
    }
}
