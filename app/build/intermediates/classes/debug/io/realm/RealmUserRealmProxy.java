package io.realm;


import android.util.JsonReader;
import android.util.JsonToken;
import com.atlantbh.mymoviesapp.model.realm.RealmMovieFavorites;
import com.atlantbh.mymoviesapp.model.realm.RealmTvFavorites;
import com.atlantbh.mymoviesapp.model.realm.RealmUser;
import io.realm.exceptions.RealmMigrationNeededException;
import io.realm.internal.ColumnInfo;
import io.realm.internal.ColumnType;
import io.realm.internal.ImplicitTransaction;
import io.realm.internal.LinkView;
import io.realm.internal.RealmObjectProxy;
import io.realm.internal.Table;
import io.realm.internal.TableOrView;
import io.realm.internal.android.JsonUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RealmUserRealmProxy extends RealmUser
    implements RealmObjectProxy {

    static final class RealmUserColumnInfo extends ColumnInfo {

        public final long idIndex;
        public final long nameIndex;
        public final long requestTokenIndex;
        public final long requestTokenExpirationIndex;
        public final long sessionIdIndex;
        public final long movieFavoritesIndex;
        public final long tvFavoritesIndex;

        RealmUserColumnInfo(String path, Table table) {
            final Map<String, Long> indicesMap = new HashMap<String, Long>(7);
            this.idIndex = getValidColumnIndex(path, table, "RealmUser", "id");
            indicesMap.put("id", this.idIndex);

            this.nameIndex = getValidColumnIndex(path, table, "RealmUser", "name");
            indicesMap.put("name", this.nameIndex);

            this.requestTokenIndex = getValidColumnIndex(path, table, "RealmUser", "requestToken");
            indicesMap.put("requestToken", this.requestTokenIndex);

            this.requestTokenExpirationIndex = getValidColumnIndex(path, table, "RealmUser", "requestTokenExpiration");
            indicesMap.put("requestTokenExpiration", this.requestTokenExpirationIndex);

            this.sessionIdIndex = getValidColumnIndex(path, table, "RealmUser", "sessionId");
            indicesMap.put("sessionId", this.sessionIdIndex);

            this.movieFavoritesIndex = getValidColumnIndex(path, table, "RealmUser", "movieFavorites");
            indicesMap.put("movieFavorites", this.movieFavoritesIndex);

            this.tvFavoritesIndex = getValidColumnIndex(path, table, "RealmUser", "tvFavorites");
            indicesMap.put("tvFavorites", this.tvFavoritesIndex);

            setIndicesMap(indicesMap);
        }
    }

    private final RealmUserColumnInfo columnInfo;
    private RealmList<RealmMovieFavorites> movieFavoritesRealmList;
    private static RealmList<RealmMovieFavorites> EMPTY_REALM_LIST_MOVIEFAVORITES;
    private RealmList<RealmTvFavorites> tvFavoritesRealmList;
    private static RealmList<RealmTvFavorites> EMPTY_REALM_LIST_TVFAVORITES;
    private static final List<String> FIELD_NAMES;
    static {
        EMPTY_REALM_LIST_MOVIEFAVORITES = new RealmList<RealmMovieFavorites>();
        EMPTY_REALM_LIST_TVFAVORITES = new RealmList<RealmTvFavorites>();
        List<String> fieldNames = new ArrayList<String>();
        fieldNames.add("id");
        fieldNames.add("name");
        fieldNames.add("requestToken");
        fieldNames.add("requestTokenExpiration");
        fieldNames.add("sessionId");
        fieldNames.add("movieFavorites");
        fieldNames.add("tvFavorites");
        FIELD_NAMES = Collections.unmodifiableList(fieldNames);
    }

    RealmUserRealmProxy(ColumnInfo columnInfo) {
        this.columnInfo = (RealmUserColumnInfo) columnInfo;
    }

    @Override
    @SuppressWarnings("cast")
    public int getId() {
        realm.checkIfValid();
        return (int) row.getLong(columnInfo.idIndex);
    }

    @Override
    public void setId(int value) {
        realm.checkIfValid();
        row.setLong(columnInfo.idIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String getName() {
        realm.checkIfValid();
        return (java.lang.String) row.getString(columnInfo.nameIndex);
    }

    @Override
    public void setName(String value) {
        realm.checkIfValid();
        if (value == null) {
            row.setNull(columnInfo.nameIndex);
            return;
        }
        row.setString(columnInfo.nameIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String getRequestToken() {
        realm.checkIfValid();
        return (java.lang.String) row.getString(columnInfo.requestTokenIndex);
    }

    @Override
    public void setRequestToken(String value) {
        realm.checkIfValid();
        if (value == null) {
            row.setNull(columnInfo.requestTokenIndex);
            return;
        }
        row.setString(columnInfo.requestTokenIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public Date getRequestTokenExpiration() {
        realm.checkIfValid();
        if (row.isNull(columnInfo.requestTokenExpirationIndex)) {
            return null;
        }
        return (java.util.Date) row.getDate(columnInfo.requestTokenExpirationIndex);
    }

    @Override
    public void setRequestTokenExpiration(Date value) {
        realm.checkIfValid();
        if (value == null) {
            row.setNull(columnInfo.requestTokenExpirationIndex);
            return;
        }
        row.setDate(columnInfo.requestTokenExpirationIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String getSessionId() {
        realm.checkIfValid();
        return (java.lang.String) row.getString(columnInfo.sessionIdIndex);
    }

    @Override
    public void setSessionId(String value) {
        realm.checkIfValid();
        if (value == null) {
            row.setNull(columnInfo.sessionIdIndex);
            return;
        }
        row.setString(columnInfo.sessionIdIndex, value);
    }

    @Override
    public RealmList<RealmMovieFavorites> getMovieFavorites() {
        realm.checkIfValid();
        // use the cached value if available
        if (movieFavoritesRealmList != null) {
            return movieFavoritesRealmList;
        } else {
            LinkView linkView = row.getLinkList(columnInfo.movieFavoritesIndex);
            if (linkView == null) {
                // return empty non managed RealmList if the LinkView is null
                // useful for non-initialized RealmObject (async query returns empty Row while the query is still running)
                return EMPTY_REALM_LIST_MOVIEFAVORITES;
            } else {
                movieFavoritesRealmList = new RealmList<RealmMovieFavorites>(RealmMovieFavorites.class, linkView, realm);
                return movieFavoritesRealmList;
            }
        }
    }

    @Override
    public void setMovieFavorites(RealmList<RealmMovieFavorites> value) {
        realm.checkIfValid();
        LinkView links = row.getLinkList(columnInfo.movieFavoritesIndex);
        links.clear();
        if (value == null) {
            return;
        }
        for (RealmObject linkedObject : (RealmList<? extends RealmObject>) value) {
            links.add(linkedObject.row.getIndex());
        }
    }

    @Override
    public RealmList<RealmTvFavorites> getTvFavorites() {
        realm.checkIfValid();
        // use the cached value if available
        if (tvFavoritesRealmList != null) {
            return tvFavoritesRealmList;
        } else {
            LinkView linkView = row.getLinkList(columnInfo.tvFavoritesIndex);
            if (linkView == null) {
                // return empty non managed RealmList if the LinkView is null
                // useful for non-initialized RealmObject (async query returns empty Row while the query is still running)
                return EMPTY_REALM_LIST_TVFAVORITES;
            } else {
                tvFavoritesRealmList = new RealmList<RealmTvFavorites>(RealmTvFavorites.class, linkView, realm);
                return tvFavoritesRealmList;
            }
        }
    }

    @Override
    public void setTvFavorites(RealmList<RealmTvFavorites> value) {
        realm.checkIfValid();
        LinkView links = row.getLinkList(columnInfo.tvFavoritesIndex);
        links.clear();
        if (value == null) {
            return;
        }
        for (RealmObject linkedObject : (RealmList<? extends RealmObject>) value) {
            links.add(linkedObject.row.getIndex());
        }
    }

    public static Table initTable(ImplicitTransaction transaction) {
        if (!transaction.hasTable("class_RealmUser")) {
            Table table = transaction.getTable("class_RealmUser");
            table.addColumn(ColumnType.INTEGER, "id", Table.NOT_NULLABLE);
            table.addColumn(ColumnType.STRING, "name", Table.NULLABLE);
            table.addColumn(ColumnType.STRING, "requestToken", Table.NULLABLE);
            table.addColumn(ColumnType.DATE, "requestTokenExpiration", Table.NULLABLE);
            table.addColumn(ColumnType.STRING, "sessionId", Table.NULLABLE);
            if (!transaction.hasTable("class_RealmMovieFavorites")) {
                RealmMovieFavoritesRealmProxy.initTable(transaction);
            }
            table.addColumnLink(ColumnType.LINK_LIST, "movieFavorites", transaction.getTable("class_RealmMovieFavorites"));
            if (!transaction.hasTable("class_RealmTvFavorites")) {
                RealmTvFavoritesRealmProxy.initTable(transaction);
            }
            table.addColumnLink(ColumnType.LINK_LIST, "tvFavorites", transaction.getTable("class_RealmTvFavorites"));
            table.addSearchIndex(table.getColumnIndex("id"));
            table.setPrimaryKey("id");
            return table;
        }
        return transaction.getTable("class_RealmUser");
    }

    public static RealmUserColumnInfo validateTable(ImplicitTransaction transaction) {
        if (transaction.hasTable("class_RealmUser")) {
            Table table = transaction.getTable("class_RealmUser");
            if (table.getColumnCount() != 7) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Field count does not match - expected 7 but was " + table.getColumnCount());
            }
            Map<String, ColumnType> columnTypes = new HashMap<String, ColumnType>();
            for (long i = 0; i < 7; i++) {
                columnTypes.put(table.getColumnName(i), table.getColumnType(i));
            }

            final RealmUserColumnInfo columnInfo = new RealmUserColumnInfo(transaction.getPath(), table);

            if (!columnTypes.containsKey("id")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing field 'id' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("id") != ColumnType.INTEGER) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid type 'int' for field 'id' in existing Realm file.");
            }
            if (table.isColumnNullable(columnInfo.idIndex)) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Field 'id' does support null values in the existing Realm file. Use corresponding boxed type for field 'id' or migrate using io.realm.internal.Table.convertColumnToNotNullable().");
            }
            if (table.getPrimaryKey() != table.getColumnIndex("id")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Primary key not defined for field 'id' in existing Realm file. Add @PrimaryKey.");
            }
            if (!table.hasSearchIndex(table.getColumnIndex("id"))) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Index not defined for field 'id' in existing Realm file. Either set @Index or migrate using io.realm.internal.Table.removeSearchIndex().");
            }
            if (!columnTypes.containsKey("name")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing field 'name' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("name") != ColumnType.STRING) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid type 'String' for field 'name' in existing Realm file.");
            }
            if (!table.isColumnNullable(columnInfo.nameIndex)) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Field 'name' is required. Either set @Required to field 'name' or migrate using io.realm.internal.Table.convertColumnToNullable().");
            }
            if (!columnTypes.containsKey("requestToken")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing field 'requestToken' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("requestToken") != ColumnType.STRING) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid type 'String' for field 'requestToken' in existing Realm file.");
            }
            if (!table.isColumnNullable(columnInfo.requestTokenIndex)) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Field 'requestToken' is required. Either set @Required to field 'requestToken' or migrate using io.realm.internal.Table.convertColumnToNullable().");
            }
            if (!columnTypes.containsKey("requestTokenExpiration")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing field 'requestTokenExpiration' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("requestTokenExpiration") != ColumnType.DATE) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid type 'Date' for field 'requestTokenExpiration' in existing Realm file.");
            }
            if (!table.isColumnNullable(columnInfo.requestTokenExpirationIndex)) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Field 'requestTokenExpiration' is required. Either set @Required to field 'requestTokenExpiration' or migrate using io.realm.internal.Table.convertColumnToNullable().");
            }
            if (!columnTypes.containsKey("sessionId")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing field 'sessionId' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("sessionId") != ColumnType.STRING) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid type 'String' for field 'sessionId' in existing Realm file.");
            }
            if (!table.isColumnNullable(columnInfo.sessionIdIndex)) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Field 'sessionId' is required. Either set @Required to field 'sessionId' or migrate using io.realm.internal.Table.convertColumnToNullable().");
            }
            if (!columnTypes.containsKey("movieFavorites")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing field 'movieFavorites'");
            }
            if (columnTypes.get("movieFavorites") != ColumnType.LINK_LIST) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid type 'RealmMovieFavorites' for field 'movieFavorites'");
            }
            if (!transaction.hasTable("class_RealmMovieFavorites")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing class 'class_RealmMovieFavorites' for field 'movieFavorites'");
            }
            Table table_5 = transaction.getTable("class_RealmMovieFavorites");
            if (!table.getLinkTarget(columnInfo.movieFavoritesIndex).hasSameSchema(table_5)) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid RealmList type for field 'movieFavorites': '" + table.getLinkTarget(columnInfo.movieFavoritesIndex).getName() + "' expected - was '" + table_5.getName() + "'");
            }
            if (!columnTypes.containsKey("tvFavorites")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing field 'tvFavorites'");
            }
            if (columnTypes.get("tvFavorites") != ColumnType.LINK_LIST) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid type 'RealmTvFavorites' for field 'tvFavorites'");
            }
            if (!transaction.hasTable("class_RealmTvFavorites")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing class 'class_RealmTvFavorites' for field 'tvFavorites'");
            }
            Table table_6 = transaction.getTable("class_RealmTvFavorites");
            if (!table.getLinkTarget(columnInfo.tvFavoritesIndex).hasSameSchema(table_6)) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid RealmList type for field 'tvFavorites': '" + table.getLinkTarget(columnInfo.tvFavoritesIndex).getName() + "' expected - was '" + table_6.getName() + "'");
            }
            return columnInfo;
        } else {
            throw new RealmMigrationNeededException(transaction.getPath(), "The RealmUser class is missing from the schema for this Realm.");
        }
    }

    public static String getTableName() {
        return "class_RealmUser";
    }

    public static List<String> getFieldNames() {
        return FIELD_NAMES;
    }

    @SuppressWarnings("cast")
    public static RealmUser createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        RealmUser obj = null;
        if (update) {
            Table table = realm.getTable(RealmUser.class);
            long pkColumnIndex = table.getPrimaryKey();
            if (!json.isNull("id")) {
                long rowIndex = table.findFirstLong(pkColumnIndex, json.getLong("id"));
                if (rowIndex != TableOrView.NO_MATCH) {
                    obj = new RealmUserRealmProxy(realm.getColumnInfo(RealmUser.class));
                    obj.realm = realm;
                    obj.row = table.getUncheckedRow(rowIndex);
                }
            }
        }
        if (obj == null) {
            obj = realm.createObject(RealmUser.class);
        }
        if (json.has("id")) {
            if (json.isNull("id")) {
                throw new IllegalArgumentException("Trying to set non-nullable field id to null.");
            } else {
                obj.setId((int) json.getInt("id"));
            }
        }
        if (json.has("name")) {
            if (json.isNull("name")) {
                obj.setName(null);
            } else {
                obj.setName((String) json.getString("name"));
            }
        }
        if (json.has("requestToken")) {
            if (json.isNull("requestToken")) {
                obj.setRequestToken(null);
            } else {
                obj.setRequestToken((String) json.getString("requestToken"));
            }
        }
        if (json.has("requestTokenExpiration")) {
            if (json.isNull("requestTokenExpiration")) {
                obj.setRequestTokenExpiration(null);
            } else {
                Object timestamp = json.get("requestTokenExpiration");
                if (timestamp instanceof String) {
                    obj.setRequestTokenExpiration(JsonUtils.stringToDate((String) timestamp));
                } else {
                    obj.setRequestTokenExpiration(new Date(json.getLong("requestTokenExpiration")));
                }
            }
        }
        if (json.has("sessionId")) {
            if (json.isNull("sessionId")) {
                obj.setSessionId(null);
            } else {
                obj.setSessionId((String) json.getString("sessionId"));
            }
        }
        if (json.has("movieFavorites")) {
            if (json.isNull("movieFavorites")) {
                obj.setMovieFavorites(null);
            } else {
                obj.getMovieFavorites().clear();
                JSONArray array = json.getJSONArray("movieFavorites");
                for (int i = 0; i < array.length(); i++) {
                    com.atlantbh.mymoviesapp.model.realm.RealmMovieFavorites item = RealmMovieFavoritesRealmProxy.createOrUpdateUsingJsonObject(realm, array.getJSONObject(i), update);
                    obj.getMovieFavorites().add(item);
                }
            }
        }
        if (json.has("tvFavorites")) {
            if (json.isNull("tvFavorites")) {
                obj.setTvFavorites(null);
            } else {
                obj.getTvFavorites().clear();
                JSONArray array = json.getJSONArray("tvFavorites");
                for (int i = 0; i < array.length(); i++) {
                    com.atlantbh.mymoviesapp.model.realm.RealmTvFavorites item = RealmTvFavoritesRealmProxy.createOrUpdateUsingJsonObject(realm, array.getJSONObject(i), update);
                    obj.getTvFavorites().add(item);
                }
            }
        }
        return obj;
    }

    @SuppressWarnings("cast")
    public static RealmUser createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        RealmUser obj = realm.createObject(RealmUser.class);
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("id")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field id to null.");
                } else {
                    obj.setId((int) reader.nextInt());
                }
            } else if (name.equals("name")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    obj.setName(null);
                } else {
                    obj.setName((String) reader.nextString());
                }
            } else if (name.equals("requestToken")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    obj.setRequestToken(null);
                } else {
                    obj.setRequestToken((String) reader.nextString());
                }
            } else if (name.equals("requestTokenExpiration")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    obj.setRequestTokenExpiration(null);
                } else if (reader.peek() == JsonToken.NUMBER) {
                    long timestamp = reader.nextLong();
                    if (timestamp > -1) {
                        obj.setRequestTokenExpiration(new Date(timestamp));
                    }
                } else {
                    obj.setRequestTokenExpiration(JsonUtils.stringToDate(reader.nextString()));
                }
            } else if (name.equals("sessionId")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    obj.setSessionId(null);
                } else {
                    obj.setSessionId((String) reader.nextString());
                }
            } else if (name.equals("movieFavorites")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    obj.setMovieFavorites(null);
                } else {
                    reader.beginArray();
                    while (reader.hasNext()) {
                        com.atlantbh.mymoviesapp.model.realm.RealmMovieFavorites item = RealmMovieFavoritesRealmProxy.createUsingJsonStream(realm, reader);
                        obj.getMovieFavorites().add(item);
                    }
                    reader.endArray();
                }
            } else if (name.equals("tvFavorites")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    obj.setTvFavorites(null);
                } else {
                    reader.beginArray();
                    while (reader.hasNext()) {
                        com.atlantbh.mymoviesapp.model.realm.RealmTvFavorites item = RealmTvFavoritesRealmProxy.createUsingJsonStream(realm, reader);
                        obj.getTvFavorites().add(item);
                    }
                    reader.endArray();
                }
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return obj;
    }

    public static RealmUser copyOrUpdate(Realm realm, RealmUser object, boolean update, Map<RealmObject,RealmObjectProxy> cache) {
        if (object.realm != null && object.realm.getPath().equals(realm.getPath())) {
            return object;
        }
        RealmUser realmObject = null;
        boolean canUpdate = update;
        if (canUpdate) {
            Table table = realm.getTable(RealmUser.class);
            long pkColumnIndex = table.getPrimaryKey();
            long rowIndex = table.findFirstLong(pkColumnIndex, object.getId());
            if (rowIndex != TableOrView.NO_MATCH) {
                realmObject = new RealmUserRealmProxy(realm.getColumnInfo(RealmUser.class));
                realmObject.realm = realm;
                realmObject.row = table.getUncheckedRow(rowIndex);
                cache.put(object, (RealmObjectProxy) realmObject);
            } else {
                canUpdate = false;
            }
        }

        if (canUpdate) {
            return update(realm, realmObject, object, cache);
        } else {
            return copy(realm, object, update, cache);
        }
    }

    public static RealmUser copy(Realm realm, RealmUser newObject, boolean update, Map<RealmObject,RealmObjectProxy> cache) {
        RealmUser realmObject = realm.createObject(RealmUser.class, newObject.getId());
        cache.put(newObject, (RealmObjectProxy) realmObject);
        realmObject.setId(newObject.getId());
        realmObject.setName(newObject.getName());
        realmObject.setRequestToken(newObject.getRequestToken());
        realmObject.setRequestTokenExpiration(newObject.getRequestTokenExpiration());
        realmObject.setSessionId(newObject.getSessionId());

        RealmList<RealmMovieFavorites> movieFavoritesList = newObject.getMovieFavorites();
        if (movieFavoritesList != null) {
            RealmList<RealmMovieFavorites> movieFavoritesRealmList = realmObject.getMovieFavorites();
            for (int i = 0; i < movieFavoritesList.size(); i++) {
                RealmMovieFavorites movieFavoritesItem = movieFavoritesList.get(i);
                RealmMovieFavorites cachemovieFavorites = (RealmMovieFavorites) cache.get(movieFavoritesItem);
                if (cachemovieFavorites != null) {
                    movieFavoritesRealmList.add(cachemovieFavorites);
                } else {
                    movieFavoritesRealmList.add(RealmMovieFavoritesRealmProxy.copyOrUpdate(realm, movieFavoritesList.get(i), update, cache));
                }
            }
        }


        RealmList<RealmTvFavorites> tvFavoritesList = newObject.getTvFavorites();
        if (tvFavoritesList != null) {
            RealmList<RealmTvFavorites> tvFavoritesRealmList = realmObject.getTvFavorites();
            for (int i = 0; i < tvFavoritesList.size(); i++) {
                RealmTvFavorites tvFavoritesItem = tvFavoritesList.get(i);
                RealmTvFavorites cachetvFavorites = (RealmTvFavorites) cache.get(tvFavoritesItem);
                if (cachetvFavorites != null) {
                    tvFavoritesRealmList.add(cachetvFavorites);
                } else {
                    tvFavoritesRealmList.add(RealmTvFavoritesRealmProxy.copyOrUpdate(realm, tvFavoritesList.get(i), update, cache));
                }
            }
        }

        return realmObject;
    }

    static RealmUser update(Realm realm, RealmUser realmObject, RealmUser newObject, Map<RealmObject, RealmObjectProxy> cache) {
        realmObject.setName(newObject.getName());
        realmObject.setRequestToken(newObject.getRequestToken());
        realmObject.setRequestTokenExpiration(newObject.getRequestTokenExpiration());
        realmObject.setSessionId(newObject.getSessionId());
        RealmList<RealmMovieFavorites> movieFavoritesList = newObject.getMovieFavorites();
        RealmList<RealmMovieFavorites> movieFavoritesRealmList = realmObject.getMovieFavorites();
        movieFavoritesRealmList.clear();
        if (movieFavoritesList != null) {
            for (int i = 0; i < movieFavoritesList.size(); i++) {
                RealmMovieFavorites movieFavoritesItem = movieFavoritesList.get(i);
                RealmMovieFavorites cachemovieFavorites = (RealmMovieFavorites) cache.get(movieFavoritesItem);
                if (cachemovieFavorites != null) {
                    movieFavoritesRealmList.add(cachemovieFavorites);
                } else {
                    movieFavoritesRealmList.add(RealmMovieFavoritesRealmProxy.copyOrUpdate(realm, movieFavoritesList.get(i), true, cache));
                }
            }
        }
        RealmList<RealmTvFavorites> tvFavoritesList = newObject.getTvFavorites();
        RealmList<RealmTvFavorites> tvFavoritesRealmList = realmObject.getTvFavorites();
        tvFavoritesRealmList.clear();
        if (tvFavoritesList != null) {
            for (int i = 0; i < tvFavoritesList.size(); i++) {
                RealmTvFavorites tvFavoritesItem = tvFavoritesList.get(i);
                RealmTvFavorites cachetvFavorites = (RealmTvFavorites) cache.get(tvFavoritesItem);
                if (cachetvFavorites != null) {
                    tvFavoritesRealmList.add(cachetvFavorites);
                } else {
                    tvFavoritesRealmList.add(RealmTvFavoritesRealmProxy.copyOrUpdate(realm, tvFavoritesList.get(i), true, cache));
                }
            }
        }
        return realmObject;
    }

    @Override
    public String toString() {
        if (!isValid()) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("RealmUser = [");
        stringBuilder.append("{id:");
        stringBuilder.append(getId());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{name:");
        stringBuilder.append(getName() != null ? getName() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{requestToken:");
        stringBuilder.append(getRequestToken() != null ? getRequestToken() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{requestTokenExpiration:");
        stringBuilder.append(getRequestTokenExpiration() != null ? getRequestTokenExpiration() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{sessionId:");
        stringBuilder.append(getSessionId() != null ? getSessionId() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{movieFavorites:");
        stringBuilder.append("RealmList<RealmMovieFavorites>[").append(getMovieFavorites().size()).append("]");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{tvFavorites:");
        stringBuilder.append("RealmList<RealmTvFavorites>[").append(getTvFavorites().size()).append("]");
        stringBuilder.append("}");
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public int hashCode() {
        String realmName = realm.getPath();
        String tableName = row.getTable().getName();
        long rowIndex = row.getIndex();

        int result = 17;
        result = 31 * result + ((realmName != null) ? realmName.hashCode() : 0);
        result = 31 * result + ((tableName != null) ? tableName.hashCode() : 0);
        result = 31 * result + (int) (rowIndex ^ (rowIndex >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RealmUserRealmProxy aRealmUser = (RealmUserRealmProxy)o;

        String path = realm.getPath();
        String otherPath = aRealmUser.realm.getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;;

        String tableName = row.getTable().getName();
        String otherTableName = aRealmUser.row.getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (row.getIndex() != aRealmUser.row.getIndex()) return false;

        return true;
    }

}
