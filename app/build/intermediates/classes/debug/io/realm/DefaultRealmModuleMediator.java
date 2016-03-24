package io.realm;


import android.util.JsonReader;
import io.realm.internal.ColumnInfo;
import io.realm.internal.ImplicitTransaction;
import io.realm.internal.RealmObjectProxy;
import io.realm.internal.RealmProxyMediator;
import io.realm.internal.Table;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;
import com.atlantbh.mymoviesapp.model.realm.RealmActor;
import com.atlantbh.mymoviesapp.model.realm.RealmGenre;
import com.atlantbh.mymoviesapp.model.realm.RealmMovie;
import com.atlantbh.mymoviesapp.model.realm.RealmMovieBasic;
import com.atlantbh.mymoviesapp.model.realm.RealmMovieFavorites;
import com.atlantbh.mymoviesapp.model.realm.RealmTvFavorites;
import com.atlantbh.mymoviesapp.model.realm.RealmUser;

@io.realm.annotations.RealmModule
class DefaultRealmModuleMediator extends RealmProxyMediator {

    private static final Set<Class<? extends RealmObject>> MODEL_CLASSES;
    static {
        Set<Class<? extends RealmObject>> modelClasses = new HashSet<Class<? extends RealmObject>>();
        modelClasses.add(RealmMovieBasic.class);
        modelClasses.add(RealmActor.class);
        modelClasses.add(RealmMovie.class);
        modelClasses.add(RealmGenre.class);
        modelClasses.add(RealmUser.class);
        modelClasses.add(RealmTvFavorites.class);
        modelClasses.add(RealmMovieFavorites.class);
        MODEL_CLASSES = Collections.unmodifiableSet(modelClasses);
    }

    @Override
    public Table createTable(Class<? extends RealmObject> clazz, ImplicitTransaction transaction) {
        checkClass(clazz);

        if (clazz.equals(RealmMovieBasic.class)) {
            return RealmMovieBasicRealmProxy.initTable(transaction);
        } else if (clazz.equals(RealmActor.class)) {
            return RealmActorRealmProxy.initTable(transaction);
        } else if (clazz.equals(RealmMovie.class)) {
            return RealmMovieRealmProxy.initTable(transaction);
        } else if (clazz.equals(RealmGenre.class)) {
            return RealmGenreRealmProxy.initTable(transaction);
        } else if (clazz.equals(RealmUser.class)) {
            return RealmUserRealmProxy.initTable(transaction);
        } else if (clazz.equals(RealmTvFavorites.class)) {
            return RealmTvFavoritesRealmProxy.initTable(transaction);
        } else if (clazz.equals(RealmMovieFavorites.class)) {
            return RealmMovieFavoritesRealmProxy.initTable(transaction);
        } else {
            throw getMissingProxyClassException(clazz);
        }
    }

    @Override
    public ColumnInfo validateTable(Class<? extends RealmObject> clazz, ImplicitTransaction transaction) {
        checkClass(clazz);

        if (clazz.equals(RealmMovieBasic.class)) {
            return RealmMovieBasicRealmProxy.validateTable(transaction);
        } else if (clazz.equals(RealmActor.class)) {
            return RealmActorRealmProxy.validateTable(transaction);
        } else if (clazz.equals(RealmMovie.class)) {
            return RealmMovieRealmProxy.validateTable(transaction);
        } else if (clazz.equals(RealmGenre.class)) {
            return RealmGenreRealmProxy.validateTable(transaction);
        } else if (clazz.equals(RealmUser.class)) {
            return RealmUserRealmProxy.validateTable(transaction);
        } else if (clazz.equals(RealmTvFavorites.class)) {
            return RealmTvFavoritesRealmProxy.validateTable(transaction);
        } else if (clazz.equals(RealmMovieFavorites.class)) {
            return RealmMovieFavoritesRealmProxy.validateTable(transaction);
        } else {
            throw getMissingProxyClassException(clazz);
        }
    }

    @Override
    public List<String> getFieldNames(Class<? extends RealmObject> clazz) {
        checkClass(clazz);

        if (clazz.equals(RealmMovieBasic.class)) {
            return RealmMovieBasicRealmProxy.getFieldNames();
        } else if (clazz.equals(RealmActor.class)) {
            return RealmActorRealmProxy.getFieldNames();
        } else if (clazz.equals(RealmMovie.class)) {
            return RealmMovieRealmProxy.getFieldNames();
        } else if (clazz.equals(RealmGenre.class)) {
            return RealmGenreRealmProxy.getFieldNames();
        } else if (clazz.equals(RealmUser.class)) {
            return RealmUserRealmProxy.getFieldNames();
        } else if (clazz.equals(RealmTvFavorites.class)) {
            return RealmTvFavoritesRealmProxy.getFieldNames();
        } else if (clazz.equals(RealmMovieFavorites.class)) {
            return RealmMovieFavoritesRealmProxy.getFieldNames();
        } else {
            throw getMissingProxyClassException(clazz);
        }
    }

    @Override
    public String getTableName(Class<? extends RealmObject> clazz) {
        checkClass(clazz);

        if (clazz.equals(RealmMovieBasic.class)) {
            return RealmMovieBasicRealmProxy.getTableName();
        } else if (clazz.equals(RealmActor.class)) {
            return RealmActorRealmProxy.getTableName();
        } else if (clazz.equals(RealmMovie.class)) {
            return RealmMovieRealmProxy.getTableName();
        } else if (clazz.equals(RealmGenre.class)) {
            return RealmGenreRealmProxy.getTableName();
        } else if (clazz.equals(RealmUser.class)) {
            return RealmUserRealmProxy.getTableName();
        } else if (clazz.equals(RealmTvFavorites.class)) {
            return RealmTvFavoritesRealmProxy.getTableName();
        } else if (clazz.equals(RealmMovieFavorites.class)) {
            return RealmMovieFavoritesRealmProxy.getTableName();
        } else {
            throw getMissingProxyClassException(clazz);
        }
    }

    @Override
    public <E extends RealmObject> E newInstance(Class<E> clazz, ColumnInfo columnInfo) {
        checkClass(clazz);

        if (clazz.equals(RealmMovieBasic.class)) {
            return clazz.cast(new RealmMovieBasicRealmProxy(columnInfo));
        } else if (clazz.equals(RealmActor.class)) {
            return clazz.cast(new RealmActorRealmProxy(columnInfo));
        } else if (clazz.equals(RealmMovie.class)) {
            return clazz.cast(new RealmMovieRealmProxy(columnInfo));
        } else if (clazz.equals(RealmGenre.class)) {
            return clazz.cast(new RealmGenreRealmProxy(columnInfo));
        } else if (clazz.equals(RealmUser.class)) {
            return clazz.cast(new RealmUserRealmProxy(columnInfo));
        } else if (clazz.equals(RealmTvFavorites.class)) {
            return clazz.cast(new RealmTvFavoritesRealmProxy(columnInfo));
        } else if (clazz.equals(RealmMovieFavorites.class)) {
            return clazz.cast(new RealmMovieFavoritesRealmProxy(columnInfo));
        } else {
            throw getMissingProxyClassException(clazz);
        }
    }

    @Override
    public Set<Class<? extends RealmObject>> getModelClasses() {
        return MODEL_CLASSES;
    }

    @Override
    public <E extends RealmObject> E copyOrUpdate(Realm realm, E obj, boolean update, Map<RealmObject, RealmObjectProxy> cache) {
        // This cast is correct because obj is either
        // generated by RealmProxy or the original type extending directly from RealmObject
        @SuppressWarnings("unchecked") Class<E> clazz = (Class<E>) ((obj instanceof RealmObjectProxy) ? obj.getClass().getSuperclass() : obj.getClass());

        if (clazz.equals(RealmMovieBasic.class)) {
            return clazz.cast(RealmMovieBasicRealmProxy.copyOrUpdate(realm, (RealmMovieBasic) obj, update, cache));
        } else if (clazz.equals(RealmActor.class)) {
            return clazz.cast(RealmActorRealmProxy.copyOrUpdate(realm, (RealmActor) obj, update, cache));
        } else if (clazz.equals(RealmMovie.class)) {
            return clazz.cast(RealmMovieRealmProxy.copyOrUpdate(realm, (RealmMovie) obj, update, cache));
        } else if (clazz.equals(RealmGenre.class)) {
            return clazz.cast(RealmGenreRealmProxy.copyOrUpdate(realm, (RealmGenre) obj, update, cache));
        } else if (clazz.equals(RealmUser.class)) {
            return clazz.cast(RealmUserRealmProxy.copyOrUpdate(realm, (RealmUser) obj, update, cache));
        } else if (clazz.equals(RealmTvFavorites.class)) {
            return clazz.cast(RealmTvFavoritesRealmProxy.copyOrUpdate(realm, (RealmTvFavorites) obj, update, cache));
        } else if (clazz.equals(RealmMovieFavorites.class)) {
            return clazz.cast(RealmMovieFavoritesRealmProxy.copyOrUpdate(realm, (RealmMovieFavorites) obj, update, cache));
        } else {
            throw getMissingProxyClassException(clazz);
        }
    }

    @Override
    public <E extends RealmObject> E createOrUpdateUsingJsonObject(Class<E> clazz, Realm realm, JSONObject json, boolean update)
        throws JSONException {
        checkClass(clazz);

        if (clazz.equals(RealmMovieBasic.class)) {
            return clazz.cast(RealmMovieBasicRealmProxy.createOrUpdateUsingJsonObject(realm, json, update));
        } else if (clazz.equals(RealmActor.class)) {
            return clazz.cast(RealmActorRealmProxy.createOrUpdateUsingJsonObject(realm, json, update));
        } else if (clazz.equals(RealmMovie.class)) {
            return clazz.cast(RealmMovieRealmProxy.createOrUpdateUsingJsonObject(realm, json, update));
        } else if (clazz.equals(RealmGenre.class)) {
            return clazz.cast(RealmGenreRealmProxy.createOrUpdateUsingJsonObject(realm, json, update));
        } else if (clazz.equals(RealmUser.class)) {
            return clazz.cast(RealmUserRealmProxy.createOrUpdateUsingJsonObject(realm, json, update));
        } else if (clazz.equals(RealmTvFavorites.class)) {
            return clazz.cast(RealmTvFavoritesRealmProxy.createOrUpdateUsingJsonObject(realm, json, update));
        } else if (clazz.equals(RealmMovieFavorites.class)) {
            return clazz.cast(RealmMovieFavoritesRealmProxy.createOrUpdateUsingJsonObject(realm, json, update));
        } else {
            throw getMissingProxyClassException(clazz);
        }
    }

    @Override
    public <E extends RealmObject> E createUsingJsonStream(Class<E> clazz, Realm realm, JsonReader reader)
        throws IOException {
        checkClass(clazz);

        if (clazz.equals(RealmMovieBasic.class)) {
            return clazz.cast(RealmMovieBasicRealmProxy.createUsingJsonStream(realm, reader));
        } else if (clazz.equals(RealmActor.class)) {
            return clazz.cast(RealmActorRealmProxy.createUsingJsonStream(realm, reader));
        } else if (clazz.equals(RealmMovie.class)) {
            return clazz.cast(RealmMovieRealmProxy.createUsingJsonStream(realm, reader));
        } else if (clazz.equals(RealmGenre.class)) {
            return clazz.cast(RealmGenreRealmProxy.createUsingJsonStream(realm, reader));
        } else if (clazz.equals(RealmUser.class)) {
            return clazz.cast(RealmUserRealmProxy.createUsingJsonStream(realm, reader));
        } else if (clazz.equals(RealmTvFavorites.class)) {
            return clazz.cast(RealmTvFavoritesRealmProxy.createUsingJsonStream(realm, reader));
        } else if (clazz.equals(RealmMovieFavorites.class)) {
            return clazz.cast(RealmMovieFavoritesRealmProxy.createUsingJsonStream(realm, reader));
        } else {
            throw getMissingProxyClassException(clazz);
        }
    }

}
