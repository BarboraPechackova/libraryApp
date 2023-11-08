package cz.cvut.fel.ear.library.dao;

import cz.cvut.fel.ear.library.model.ProfilePicture;
import org.springframework.stereotype.Repository;

@Repository
public class ProfilePictureDao extends PictureDao<ProfilePicture> {
    public ProfilePictureDao() {
        super(ProfilePicture.class);
    }
}
