package cz.cvut.fel.ear.library.dao;


// TODO: deterine if this class can be abstract or not
public abstract class PictureDao<Picture> extends BaseDao<Picture> {

    public PictureDao(Class<Picture> type) {
        super(type);
    }
}
