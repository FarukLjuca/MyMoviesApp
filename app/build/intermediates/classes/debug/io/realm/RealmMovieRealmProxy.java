package io.realm;


import android.util.JsonReader;
import android.util.JsonToken;
import com.atlantbh.mymoviesapp.model.realm.RealmActor;
import com.atlantbh.mymoviesapp.model.realm.RealmGenre;
import com.atlantbh.mymoviesapp.model.realm.RealmMovie;
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

public class RealmMovieRealmProxy extends RealmMovie
    implements RealmObjectProxy {

    static final class RealmMovieColumnInfo extends ColumnInfo {

        public final long idIndex;
        public final long indexPopularIndex;
        public final long indexNowPlayingIndex;
        public final long indexTopRatedIndex;
        public final long categoryIndex;
        public final long posterPathIndex;
        public final long overviewIndex;
        public final long titleIndex;
        public final long voteAverageIndex;
        public final long backdropPathIndex;
        public final long voteCountIndex;
        public final long releaseDateIndex;
        public final long genresIndex;
        public final long actorsIndex;
        public final long runtimeIndex;

        RealmMovieColumnInfo(String path, Table table) {
            final Map<String, Long> indicesMap = new HashMap<String, Long>(15);
            this.idIndex = getValidColumnIndex(path, table, "RealmMovie", "id");
            indicesMap.put("id", this.idIndex);

            this.indexPopularIndex = getValidColumnIndex(path, table, "RealmMovie", "indexPopular");
            indicesMap.put("indexPopular", this.indexPopularIndex);

            this.indexNowPlayingIndex = getValidColumnIndex(path, table, "RealmMovie", "indexNowPlaying");
            indicesMap.put("indexNowPlaying", this.indexNowPlayingIndex);

            this.indexTopRatedIndex = getValidColumnIndex(path, table, "RealmMovie", "indexTopRated");
            indicesMap.put("indexTopRated", this.indexTopRatedIndex);

            this.categoryIndex = getValidColumnIndex(path, table, "RealmMovie", "category");
            indicesMap.put("category", this.categoryIndex);

            this.posterPathIndex = getValidColumnIndex(path, table, "RealmMovie", "posterPath");
            indicesMap.put("posterPath", this.posterPathIndex);

            this.overviewIndex = getValidColumnIndex(path, table, "RealmMovie", "overview");
            indicesMap.put("overview", this.overviewIndex);

            this.titleIndex = getValidColumnIndex(path, table, "RealmMovie", "title");
            indicesMap.put("title", this.titleIndex);

            this.voteAverageIndex = getValidColumnIndex(path, table, "RealmMovie", "voteAverage");
            indicesMap.put("voteAverage", this.voteAverageIndex);

            this.backdropPathIndex = getValidColumnIndex(path, table, "RealmMovie", "backdropPath");
            indicesMap.put("backdropPath", this.backdropPathIndex);

            this.voteCountIndex = getValidColumnIndex(path, table, "RealmMovie", "voteCount");
            indicesMap.put("voteCount", this.voteCountIndex);

            this.releaseDateIndex = getValidColumnIndex(path, table, "RealmMovie", "releaseDate");
            indicesMap.put("releaseDate", this.releaseDateIndex);

            this.genresIndex = getValidColumnIndex(path, table, "RealmMovie", "genres");
            indicesMap.put("genres", this.genresIndex);

            this.actorsIndex = getValidColumnIndex(path, table, "RealmMovie", "actors");
            indicesMap.put("actors", this.actorsIndex);

            this.runtimeIndex = getValidColumnIndex(path, table, "RealmMovie", "runtime");
            indicesMap.put("runtime", this.runtimeIndex);

            setIndicesMap(indicesMap);
        }
    }

    private final RealmMovieColumnInfo columnInfo;
    private RealmList<RealmGenre> genresRealmList;
    private static RealmList<RealmGenre> EMPTY_REALM_LIST_GENRES;
    private RealmList<RealmActor> actorsRealmList;
    private static RealmList<RealmActor> EMPTY_REALM_LIST_ACTORS;
    private static final List<String> FIELD_NAMES;
    static {
        EMPTY_REALM_LIST_GENRES = new RealmList<RealmGenre>();
        EMPTY_REALM_LIST_ACTORS = new RealmList<RealmActor>();
        List<String> fieldNames = new ArrayList<String>();
        fieldNames.add("id");
        fieldNames.add("indexPopular");
        fieldNames.add("indexNowPlaying");
        fieldNames.add("indexTopRated");
        fieldNames.add("category");
        fieldNames.add("posterPath");
        fieldNames.add("overview");
        fieldNames.add("title");
        fieldNames.add("voteAverage");
        fieldNames.add("backdropPath");
        fieldNames.add("voteCount");
        fieldNames.add("releaseDate");
        fieldNames.add("genres");
        fieldNames.add("actors");
        fieldNames.add("runtime");
        FIELD_NAMES = Collections.unmodifiableList(fieldNames);
    }

    RealmMovieRealmProxy(ColumnInfo columnInfo) {
        this.columnInfo = (RealmMovieColumnInfo) columnInfo;
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
    public int getIndexPopular() {
        realm.checkIfValid();
        return (int) row.getLong(columnInfo.indexPopularIndex);
    }

    @Override
    public void setIndexPopular(int value) {
        realm.checkIfValid();
        row.setLong(columnInfo.indexPopularIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public int getIndexNowPlaying() {
        realm.checkIfValid();
        return (int) row.getLong(columnInfo.indexNowPlayingIndex);
    }

    @Override
    public void setIndexNowPlaying(int value) {
        realm.checkIfValid();
        row.setLong(columnInfo.indexNowPlayingIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public int getIndexTopRated() {
        realm.checkIfValid();
        return (int) row.getLong(columnInfo.indexTopRatedIndex);
    }

    @Override
    public void setIndexTopRated(int value) {
        realm.checkIfValid();
        row.setLong(columnInfo.indexTopRatedIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public int getCategory() {
        realm.checkIfValid();
        return (int) row.getLong(columnInfo.categoryIndex);
    }

    @Override
    public void setCategory(int value) {
        realm.checkIfValid();
        row.setLong(columnInfo.categoryIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String getPosterPath() {
        realm.checkIfValid();
        return (java.lang.String) row.getString(columnInfo.posterPathIndex);
    }

    @Override
    public void setPosterPath(String value) {
        realm.checkIfValid();
        if (value == null) {
            row.setNull(columnInfo.posterPathIndex);
            return;
        }
        row.setString(columnInfo.posterPathIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String getOverview() {
        realm.checkIfValid();
        return (java.lang.String) row.getString(columnInfo.overviewIndex);
    }

    @Override
    public void setOverview(String value) {
        realm.checkIfValid();
        if (value == null) {
            row.setNull(columnInfo.overviewIndex);
            return;
        }
        row.setString(columnInfo.overviewIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String getTitle() {
        realm.checkIfValid();
        return (java.lang.String) row.getString(columnInfo.titleIndex);
    }

    @Override
    public void setTitle(String value) {
        realm.checkIfValid();
        if (value == null) {
            row.setNull(columnInfo.titleIndex);
            return;
        }
        row.setString(columnInfo.titleIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public Float getVoteAverage() {
        realm.checkIfValid();
        if (row.isNull(columnInfo.voteAverageIndex)) {
            return null;
        }
        return (float) row.getFloat(columnInfo.voteAverageIndex);
    }

    @Override
    public void setVoteAverage(Float value) {
        realm.checkIfValid();
        if (value == null) {
            row.setNull(columnInfo.voteAverageIndex);
            return;
        }
        row.setFloat(columnInfo.voteAverageIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String getBackdropPath() {
        realm.checkIfValid();
        return (java.lang.String) row.getString(columnInfo.backdropPathIndex);
    }

    @Override
    public void setBackdropPath(String value) {
        realm.checkIfValid();
        if (value == null) {
            row.setNull(columnInfo.backdropPathIndex);
            return;
        }
        row.setString(columnInfo.backdropPathIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public int getVoteCount() {
        realm.checkIfValid();
        return (int) row.getLong(columnInfo.voteCountIndex);
    }

    @Override
    public void setVoteCount(int value) {
        realm.checkIfValid();
        row.setLong(columnInfo.voteCountIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public Date getReleaseDate() {
        realm.checkIfValid();
        if (row.isNull(columnInfo.releaseDateIndex)) {
            return null;
        }
        return (java.util.Date) row.getDate(columnInfo.releaseDateIndex);
    }

    @Override
    public void setReleaseDate(Date value) {
        realm.checkIfValid();
        if (value == null) {
            row.setNull(columnInfo.releaseDateIndex);
            return;
        }
        row.setDate(columnInfo.releaseDateIndex, value);
    }

    @Override
    public RealmList<RealmGenre> getGenres() {
        realm.checkIfValid();
        // use the cached value if available
        if (genresRealmList != null) {
            return genresRealmList;
        } else {
            LinkView linkView = row.getLinkList(columnInfo.genresIndex);
            if (linkView == null) {
                // return empty non managed RealmList if the LinkView is null
                // useful for non-initialized RealmObject (async query returns empty Row while the query is still running)
                return EMPTY_REALM_LIST_GENRES;
            } else {
                genresRealmList = new RealmList<RealmGenre>(RealmGenre.class, linkView, realm);
                return genresRealmList;
            }
        }
    }

    @Override
    public void setGenres(RealmList<RealmGenre> value) {
        realm.checkIfValid();
        LinkView links = row.getLinkList(columnInfo.genresIndex);
        links.clear();
        if (value == null) {
            return;
        }
        for (RealmObject linkedObject : (RealmList<? extends RealmObject>) value) {
            links.add(linkedObject.row.getIndex());
        }
    }

    @Override
    public RealmList<RealmActor> getActors() {
        realm.checkIfValid();
        // use the cached value if available
        if (actorsRealmList != null) {
            return actorsRealmList;
        } else {
            LinkView linkView = row.getLinkList(columnInfo.actorsIndex);
            if (linkView == null) {
                // return empty non managed RealmList if the LinkView is null
                // useful for non-initialized RealmObject (async query returns empty Row while the query is still running)
                return EMPTY_REALM_LIST_ACTORS;
            } else {
                actorsRealmList = new RealmList<RealmActor>(RealmActor.class, linkView, realm);
                return actorsRealmList;
            }
        }
    }

    @Override
    public void setActors(RealmList<RealmActor> value) {
        realm.checkIfValid();
        LinkView links = row.getLinkList(columnInfo.actorsIndex);
        links.clear();
        if (value == null) {
            return;
        }
        for (RealmObject linkedObject : (RealmList<? extends RealmObject>) value) {
            links.add(linkedObject.row.getIndex());
        }
    }

    @Override
    @SuppressWarnings("cast")
    public int getRuntime() {
        realm.checkIfValid();
        return (int) row.getLong(columnInfo.runtimeIndex);
    }

    @Override
    public void setRuntime(int value) {
        realm.checkIfValid();
        row.setLong(columnInfo.runtimeIndex, value);
    }

    public static Table initTable(ImplicitTransaction transaction) {
        if (!transaction.hasTable("class_RealmMovie")) {
            Table table = transaction.getTable("class_RealmMovie");
            table.addColumn(ColumnType.INTEGER, "id", Table.NOT_NULLABLE);
            table.addColumn(ColumnType.INTEGER, "indexPopular", Table.NOT_NULLABLE);
            table.addColumn(ColumnType.INTEGER, "indexNowPlaying", Table.NOT_NULLABLE);
            table.addColumn(ColumnType.INTEGER, "indexTopRated", Table.NOT_NULLABLE);
            table.addColumn(ColumnType.INTEGER, "category", Table.NOT_NULLABLE);
            table.addColumn(ColumnType.STRING, "posterPath", Table.NULLABLE);
            table.addColumn(ColumnType.STRING, "overview", Table.NULLABLE);
            table.addColumn(ColumnType.STRING, "title", Table.NULLABLE);
            table.addColumn(ColumnType.FLOAT, "voteAverage", Table.NULLABLE);
            table.addColumn(ColumnType.STRING, "backdropPath", Table.NULLABLE);
            table.addColumn(ColumnType.INTEGER, "voteCount", Table.NOT_NULLABLE);
            table.addColumn(ColumnType.DATE, "releaseDate", Table.NULLABLE);
            if (!transaction.hasTable("class_RealmGenre")) {
                RealmGenreRealmProxy.initTable(transaction);
            }
            table.addColumnLink(ColumnType.LINK_LIST, "genres", transaction.getTable("class_RealmGenre"));
            if (!transaction.hasTable("class_RealmActor")) {
                RealmActorRealmProxy.initTable(transaction);
            }
            table.addColumnLink(ColumnType.LINK_LIST, "actors", transaction.getTable("class_RealmActor"));
            table.addColumn(ColumnType.INTEGER, "runtime", Table.NOT_NULLABLE);
            table.addSearchIndex(table.getColumnIndex("id"));
            table.setPrimaryKey("id");
            return table;
        }
        return transaction.getTable("class_RealmMovie");
    }

    public static RealmMovieColumnInfo validateTable(ImplicitTransaction transaction) {
        if (transaction.hasTable("class_RealmMovie")) {
            Table table = transaction.getTable("class_RealmMovie");
            if (table.getColumnCount() != 15) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Field count does not match - expected 15 but was " + table.getColumnCount());
            }
            Map<String, ColumnType> columnTypes = new HashMap<String, ColumnType>();
            for (long i = 0; i < 15; i++) {
                columnTypes.put(table.getColumnName(i), table.getColumnType(i));
            }

            final RealmMovieColumnInfo columnInfo = new RealmMovieColumnInfo(transaction.getPath(), table);

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
            if (!columnTypes.containsKey("indexPopular")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing field 'indexPopular' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("indexPopular") != ColumnType.INTEGER) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid type 'int' for field 'indexPopular' in existing Realm file.");
            }
            if (table.isColumnNullable(columnInfo.indexPopularIndex)) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Field 'indexPopular' does support null values in the existing Realm file. Use corresponding boxed type for field 'indexPopular' or migrate using io.realm.internal.Table.convertColumnToNotNullable().");
            }
            if (!columnTypes.containsKey("indexNowPlaying")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing field 'indexNowPlaying' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("indexNowPlaying") != ColumnType.INTEGER) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid type 'int' for field 'indexNowPlaying' in existing Realm file.");
            }
            if (table.isColumnNullable(columnInfo.indexNowPlayingIndex)) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Field 'indexNowPlaying' does support null values in the existing Realm file. Use corresponding boxed type for field 'indexNowPlaying' or migrate using io.realm.internal.Table.convertColumnToNotNullable().");
            }
            if (!columnTypes.containsKey("indexTopRated")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing field 'indexTopRated' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("indexTopRated") != ColumnType.INTEGER) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid type 'int' for field 'indexTopRated' in existing Realm file.");
            }
            if (table.isColumnNullable(columnInfo.indexTopRatedIndex)) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Field 'indexTopRated' does support null values in the existing Realm file. Use corresponding boxed type for field 'indexTopRated' or migrate using io.realm.internal.Table.convertColumnToNotNullable().");
            }
            if (!columnTypes.containsKey("category")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing field 'category' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("category") != ColumnType.INTEGER) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid type 'int' for field 'category' in existing Realm file.");
            }
            if (table.isColumnNullable(columnInfo.categoryIndex)) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Field 'category' does support null values in the existing Realm file. Use corresponding boxed type for field 'category' or migrate using io.realm.internal.Table.convertColumnToNotNullable().");
            }
            if (!columnTypes.containsKey("posterPath")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing field 'posterPath' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("posterPath") != ColumnType.STRING) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid type 'String' for field 'posterPath' in existing Realm file.");
            }
            if (!table.isColumnNullable(columnInfo.posterPathIndex)) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Field 'posterPath' is required. Either set @Required to field 'posterPath' or migrate using io.realm.internal.Table.convertColumnToNullable().");
            }
            if (!columnTypes.containsKey("overview")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing field 'overview' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("overview") != ColumnType.STRING) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid type 'String' for field 'overview' in existing Realm file.");
            }
            if (!table.isColumnNullable(columnInfo.overviewIndex)) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Field 'overview' is required. Either set @Required to field 'overview' or migrate using io.realm.internal.Table.convertColumnToNullable().");
            }
            if (!columnTypes.containsKey("title")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing field 'title' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("title") != ColumnType.STRING) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid type 'String' for field 'title' in existing Realm file.");
            }
            if (!table.isColumnNullable(columnInfo.titleIndex)) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Field 'title' is required. Either set @Required to field 'title' or migrate using io.realm.internal.Table.convertColumnToNullable().");
            }
            if (!columnTypes.containsKey("voteAverage")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing field 'voteAverage' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("voteAverage") != ColumnType.FLOAT) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid type 'Float' for field 'voteAverage' in existing Realm file.");
            }
            if (!table.isColumnNullable(columnInfo.voteAverageIndex)) {
                throw new RealmMigrationNeededException(transaction.getPath(),"Field 'voteAverage' does not support null values in the existing Realm file. Either set @Required, use the primitive type for field 'voteAverage' or migrate using io.realm.internal.Table.convertColumnToNullable().");
            }
            if (!columnTypes.containsKey("backdropPath")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing field 'backdropPath' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("backdropPath") != ColumnType.STRING) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid type 'String' for field 'backdropPath' in existing Realm file.");
            }
            if (!table.isColumnNullable(columnInfo.backdropPathIndex)) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Field 'backdropPath' is required. Either set @Required to field 'backdropPath' or migrate using io.realm.internal.Table.convertColumnToNullable().");
            }
            if (!columnTypes.containsKey("voteCount")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing field 'voteCount' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("voteCount") != ColumnType.INTEGER) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid type 'int' for field 'voteCount' in existing Realm file.");
            }
            if (table.isColumnNullable(columnInfo.voteCountIndex)) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Field 'voteCount' does support null values in the existing Realm file. Use corresponding boxed type for field 'voteCount' or migrate using io.realm.internal.Table.convertColumnToNotNullable().");
            }
            if (!columnTypes.containsKey("releaseDate")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing field 'releaseDate' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("releaseDate") != ColumnType.DATE) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid type 'Date' for field 'releaseDate' in existing Realm file.");
            }
            if (!table.isColumnNullable(columnInfo.releaseDateIndex)) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Field 'releaseDate' is required. Either set @Required to field 'releaseDate' or migrate using io.realm.internal.Table.convertColumnToNullable().");
            }
            if (!columnTypes.containsKey("genres")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing field 'genres'");
            }
            if (columnTypes.get("genres") != ColumnType.LINK_LIST) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid type 'RealmGenre' for field 'genres'");
            }
            if (!transaction.hasTable("class_RealmGenre")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing class 'class_RealmGenre' for field 'genres'");
            }
            Table table_12 = transaction.getTable("class_RealmGenre");
            if (!table.getLinkTarget(columnInfo.genresIndex).hasSameSchema(table_12)) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid RealmList type for field 'genres': '" + table.getLinkTarget(columnInfo.genresIndex).getName() + "' expected - was '" + table_12.getName() + "'");
            }
            if (!columnTypes.containsKey("actors")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing field 'actors'");
            }
            if (columnTypes.get("actors") != ColumnType.LINK_LIST) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid type 'RealmActor' for field 'actors'");
            }
            if (!transaction.hasTable("class_RealmActor")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing class 'class_RealmActor' for field 'actors'");
            }
            Table table_13 = transaction.getTable("class_RealmActor");
            if (!table.getLinkTarget(columnInfo.actorsIndex).hasSameSchema(table_13)) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid RealmList type for field 'actors': '" + table.getLinkTarget(columnInfo.actorsIndex).getName() + "' expected - was '" + table_13.getName() + "'");
            }
            if (!columnTypes.containsKey("runtime")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing field 'runtime' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("runtime") != ColumnType.INTEGER) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid type 'int' for field 'runtime' in existing Realm file.");
            }
            if (table.isColumnNullable(columnInfo.runtimeIndex)) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Field 'runtime' does support null values in the existing Realm file. Use corresponding boxed type for field 'runtime' or migrate using io.realm.internal.Table.convertColumnToNotNullable().");
            }
            return columnInfo;
        } else {
            throw new RealmMigrationNeededException(transaction.getPath(), "The RealmMovie class is missing from the schema for this Realm.");
        }
    }

    public static String getTableName() {
        return "class_RealmMovie";
    }

    public static List<String> getFieldNames() {
        return FIELD_NAMES;
    }

    @SuppressWarnings("cast")
    public static RealmMovie createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        RealmMovie obj = null;
        if (update) {
            Table table = realm.getTable(RealmMovie.class);
            long pkColumnIndex = table.getPrimaryKey();
            if (!json.isNull("id")) {
                long rowIndex = table.findFirstLong(pkColumnIndex, json.getLong("id"));
                if (rowIndex != TableOrView.NO_MATCH) {
                    obj = new RealmMovieRealmProxy(realm.getColumnInfo(RealmMovie.class));
                    obj.realm = realm;
                    obj.row = table.getUncheckedRow(rowIndex);
                }
            }
        }
        if (obj == null) {
            obj = realm.createObject(RealmMovie.class);
        }
        if (json.has("id")) {
            if (json.isNull("id")) {
                throw new IllegalArgumentException("Trying to set non-nullable field id to null.");
            } else {
                obj.setId((int) json.getInt("id"));
            }
        }
        if (json.has("indexPopular")) {
            if (json.isNull("indexPopular")) {
                throw new IllegalArgumentException("Trying to set non-nullable field indexPopular to null.");
            } else {
                obj.setIndexPopular((int) json.getInt("indexPopular"));
            }
        }
        if (json.has("indexNowPlaying")) {
            if (json.isNull("indexNowPlaying")) {
                throw new IllegalArgumentException("Trying to set non-nullable field indexNowPlaying to null.");
            } else {
                obj.setIndexNowPlaying((int) json.getInt("indexNowPlaying"));
            }
        }
        if (json.has("indexTopRated")) {
            if (json.isNull("indexTopRated")) {
                throw new IllegalArgumentException("Trying to set non-nullable field indexTopRated to null.");
            } else {
                obj.setIndexTopRated((int) json.getInt("indexTopRated"));
            }
        }
        if (json.has("category")) {
            if (json.isNull("category")) {
                throw new IllegalArgumentException("Trying to set non-nullable field category to null.");
            } else {
                obj.setCategory((int) json.getInt("category"));
            }
        }
        if (json.has("posterPath")) {
            if (json.isNull("posterPath")) {
                obj.setPosterPath(null);
            } else {
                obj.setPosterPath((String) json.getString("posterPath"));
            }
        }
        if (json.has("overview")) {
            if (json.isNull("overview")) {
                obj.setOverview(null);
            } else {
                obj.setOverview((String) json.getString("overview"));
            }
        }
        if (json.has("title")) {
            if (json.isNull("title")) {
                obj.setTitle(null);
            } else {
                obj.setTitle((String) json.getString("title"));
            }
        }
        if (json.has("voteAverage")) {
            if (json.isNull("voteAverage")) {
                obj.setVoteAverage(null);
            } else {
                obj.setVoteAverage((float) json.getDouble("voteAverage"));
            }
        }
        if (json.has("backdropPath")) {
            if (json.isNull("backdropPath")) {
                obj.setBackdropPath(null);
            } else {
                obj.setBackdropPath((String) json.getString("backdropPath"));
            }
        }
        if (json.has("voteCount")) {
            if (json.isNull("voteCount")) {
                throw new IllegalArgumentException("Trying to set non-nullable field voteCount to null.");
            } else {
                obj.setVoteCount((int) json.getInt("voteCount"));
            }
        }
        if (json.has("releaseDate")) {
            if (json.isNull("releaseDate")) {
                obj.setReleaseDate(null);
            } else {
                Object timestamp = json.get("releaseDate");
                if (timestamp instanceof String) {
                    obj.setReleaseDate(JsonUtils.stringToDate((String) timestamp));
                } else {
                    obj.setReleaseDate(new Date(json.getLong("releaseDate")));
                }
            }
        }
        if (json.has("genres")) {
            if (json.isNull("genres")) {
                obj.setGenres(null);
            } else {
                obj.getGenres().clear();
                JSONArray array = json.getJSONArray("genres");
                for (int i = 0; i < array.length(); i++) {
                    com.atlantbh.mymoviesapp.model.realm.RealmGenre item = RealmGenreRealmProxy.createOrUpdateUsingJsonObject(realm, array.getJSONObject(i), update);
                    obj.getGenres().add(item);
                }
            }
        }
        if (json.has("actors")) {
            if (json.isNull("actors")) {
                obj.setActors(null);
            } else {
                obj.getActors().clear();
                JSONArray array = json.getJSONArray("actors");
                for (int i = 0; i < array.length(); i++) {
                    com.atlantbh.mymoviesapp.model.realm.RealmActor item = RealmActorRealmProxy.createOrUpdateUsingJsonObject(realm, array.getJSONObject(i), update);
                    obj.getActors().add(item);
                }
            }
        }
        if (json.has("runtime")) {
            if (json.isNull("runtime")) {
                throw new IllegalArgumentException("Trying to set non-nullable field runtime to null.");
            } else {
                obj.setRuntime((int) json.getInt("runtime"));
            }
        }
        return obj;
    }

    @SuppressWarnings("cast")
    public static RealmMovie createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        RealmMovie obj = realm.createObject(RealmMovie.class);
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
            } else if (name.equals("indexPopular")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field indexPopular to null.");
                } else {
                    obj.setIndexPopular((int) reader.nextInt());
                }
            } else if (name.equals("indexNowPlaying")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field indexNowPlaying to null.");
                } else {
                    obj.setIndexNowPlaying((int) reader.nextInt());
                }
            } else if (name.equals("indexTopRated")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field indexTopRated to null.");
                } else {
                    obj.setIndexTopRated((int) reader.nextInt());
                }
            } else if (name.equals("category")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field category to null.");
                } else {
                    obj.setCategory((int) reader.nextInt());
                }
            } else if (name.equals("posterPath")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    obj.setPosterPath(null);
                } else {
                    obj.setPosterPath((String) reader.nextString());
                }
            } else if (name.equals("overview")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    obj.setOverview(null);
                } else {
                    obj.setOverview((String) reader.nextString());
                }
            } else if (name.equals("title")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    obj.setTitle(null);
                } else {
                    obj.setTitle((String) reader.nextString());
                }
            } else if (name.equals("voteAverage")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    obj.setVoteAverage(null);
                } else {
                    obj.setVoteAverage((float) reader.nextDouble());
                }
            } else if (name.equals("backdropPath")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    obj.setBackdropPath(null);
                } else {
                    obj.setBackdropPath((String) reader.nextString());
                }
            } else if (name.equals("voteCount")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field voteCount to null.");
                } else {
                    obj.setVoteCount((int) reader.nextInt());
                }
            } else if (name.equals("releaseDate")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    obj.setReleaseDate(null);
                } else if (reader.peek() == JsonToken.NUMBER) {
                    long timestamp = reader.nextLong();
                    if (timestamp > -1) {
                        obj.setReleaseDate(new Date(timestamp));
                    }
                } else {
                    obj.setReleaseDate(JsonUtils.stringToDate(reader.nextString()));
                }
            } else if (name.equals("genres")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    obj.setGenres(null);
                } else {
                    reader.beginArray();
                    while (reader.hasNext()) {
                        com.atlantbh.mymoviesapp.model.realm.RealmGenre item = RealmGenreRealmProxy.createUsingJsonStream(realm, reader);
                        obj.getGenres().add(item);
                    }
                    reader.endArray();
                }
            } else if (name.equals("actors")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    obj.setActors(null);
                } else {
                    reader.beginArray();
                    while (reader.hasNext()) {
                        com.atlantbh.mymoviesapp.model.realm.RealmActor item = RealmActorRealmProxy.createUsingJsonStream(realm, reader);
                        obj.getActors().add(item);
                    }
                    reader.endArray();
                }
            } else if (name.equals("runtime")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field runtime to null.");
                } else {
                    obj.setRuntime((int) reader.nextInt());
                }
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return obj;
    }

    public static RealmMovie copyOrUpdate(Realm realm, RealmMovie object, boolean update, Map<RealmObject,RealmObjectProxy> cache) {
        if (object.realm != null && object.realm.getPath().equals(realm.getPath())) {
            return object;
        }
        RealmMovie realmObject = null;
        boolean canUpdate = update;
        if (canUpdate) {
            Table table = realm.getTable(RealmMovie.class);
            long pkColumnIndex = table.getPrimaryKey();
            long rowIndex = table.findFirstLong(pkColumnIndex, object.getId());
            if (rowIndex != TableOrView.NO_MATCH) {
                realmObject = new RealmMovieRealmProxy(realm.getColumnInfo(RealmMovie.class));
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

    public static RealmMovie copy(Realm realm, RealmMovie newObject, boolean update, Map<RealmObject,RealmObjectProxy> cache) {
        RealmMovie realmObject = realm.createObject(RealmMovie.class, newObject.getId());
        cache.put(newObject, (RealmObjectProxy) realmObject);
        realmObject.setId(newObject.getId());
        realmObject.setIndexPopular(newObject.getIndexPopular());
        realmObject.setIndexNowPlaying(newObject.getIndexNowPlaying());
        realmObject.setIndexTopRated(newObject.getIndexTopRated());
        realmObject.setCategory(newObject.getCategory());
        realmObject.setPosterPath(newObject.getPosterPath());
        realmObject.setOverview(newObject.getOverview());
        realmObject.setTitle(newObject.getTitle());
        realmObject.setVoteAverage(newObject.getVoteAverage());
        realmObject.setBackdropPath(newObject.getBackdropPath());
        realmObject.setVoteCount(newObject.getVoteCount());
        realmObject.setReleaseDate(newObject.getReleaseDate());

        RealmList<RealmGenre> genresList = newObject.getGenres();
        if (genresList != null) {
            RealmList<RealmGenre> genresRealmList = realmObject.getGenres();
            for (int i = 0; i < genresList.size(); i++) {
                RealmGenre genresItem = genresList.get(i);
                RealmGenre cachegenres = (RealmGenre) cache.get(genresItem);
                if (cachegenres != null) {
                    genresRealmList.add(cachegenres);
                } else {
                    genresRealmList.add(RealmGenreRealmProxy.copyOrUpdate(realm, genresList.get(i), update, cache));
                }
            }
        }


        RealmList<RealmActor> actorsList = newObject.getActors();
        if (actorsList != null) {
            RealmList<RealmActor> actorsRealmList = realmObject.getActors();
            for (int i = 0; i < actorsList.size(); i++) {
                RealmActor actorsItem = actorsList.get(i);
                RealmActor cacheactors = (RealmActor) cache.get(actorsItem);
                if (cacheactors != null) {
                    actorsRealmList.add(cacheactors);
                } else {
                    actorsRealmList.add(RealmActorRealmProxy.copyOrUpdate(realm, actorsList.get(i), update, cache));
                }
            }
        }

        realmObject.setRuntime(newObject.getRuntime());
        return realmObject;
    }

    static RealmMovie update(Realm realm, RealmMovie realmObject, RealmMovie newObject, Map<RealmObject, RealmObjectProxy> cache) {
        realmObject.setIndexPopular(newObject.getIndexPopular());
        realmObject.setIndexNowPlaying(newObject.getIndexNowPlaying());
        realmObject.setIndexTopRated(newObject.getIndexTopRated());
        realmObject.setCategory(newObject.getCategory());
        realmObject.setPosterPath(newObject.getPosterPath());
        realmObject.setOverview(newObject.getOverview());
        realmObject.setTitle(newObject.getTitle());
        realmObject.setVoteAverage(newObject.getVoteAverage());
        realmObject.setBackdropPath(newObject.getBackdropPath());
        realmObject.setVoteCount(newObject.getVoteCount());
        realmObject.setReleaseDate(newObject.getReleaseDate());
        RealmList<RealmGenre> genresList = newObject.getGenres();
        RealmList<RealmGenre> genresRealmList = realmObject.getGenres();
        genresRealmList.clear();
        if (genresList != null) {
            for (int i = 0; i < genresList.size(); i++) {
                RealmGenre genresItem = genresList.get(i);
                RealmGenre cachegenres = (RealmGenre) cache.get(genresItem);
                if (cachegenres != null) {
                    genresRealmList.add(cachegenres);
                } else {
                    genresRealmList.add(RealmGenreRealmProxy.copyOrUpdate(realm, genresList.get(i), true, cache));
                }
            }
        }
        RealmList<RealmActor> actorsList = newObject.getActors();
        RealmList<RealmActor> actorsRealmList = realmObject.getActors();
        actorsRealmList.clear();
        if (actorsList != null) {
            for (int i = 0; i < actorsList.size(); i++) {
                RealmActor actorsItem = actorsList.get(i);
                RealmActor cacheactors = (RealmActor) cache.get(actorsItem);
                if (cacheactors != null) {
                    actorsRealmList.add(cacheactors);
                } else {
                    actorsRealmList.add(RealmActorRealmProxy.copyOrUpdate(realm, actorsList.get(i), true, cache));
                }
            }
        }
        realmObject.setRuntime(newObject.getRuntime());
        return realmObject;
    }

    @Override
    public String toString() {
        if (!isValid()) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("RealmMovie = [");
        stringBuilder.append("{id:");
        stringBuilder.append(getId());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{indexPopular:");
        stringBuilder.append(getIndexPopular());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{indexNowPlaying:");
        stringBuilder.append(getIndexNowPlaying());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{indexTopRated:");
        stringBuilder.append(getIndexTopRated());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{category:");
        stringBuilder.append(getCategory());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{posterPath:");
        stringBuilder.append(getPosterPath() != null ? getPosterPath() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{overview:");
        stringBuilder.append(getOverview() != null ? getOverview() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{title:");
        stringBuilder.append(getTitle() != null ? getTitle() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{voteAverage:");
        stringBuilder.append(getVoteAverage() != null ? getVoteAverage() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{backdropPath:");
        stringBuilder.append(getBackdropPath() != null ? getBackdropPath() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{voteCount:");
        stringBuilder.append(getVoteCount());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{releaseDate:");
        stringBuilder.append(getReleaseDate() != null ? getReleaseDate() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{genres:");
        stringBuilder.append("RealmList<RealmGenre>[").append(getGenres().size()).append("]");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{actors:");
        stringBuilder.append("RealmList<RealmActor>[").append(getActors().size()).append("]");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{runtime:");
        stringBuilder.append(getRuntime());
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
        RealmMovieRealmProxy aRealmMovie = (RealmMovieRealmProxy)o;

        String path = realm.getPath();
        String otherPath = aRealmMovie.realm.getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;;

        String tableName = row.getTable().getName();
        String otherTableName = aRealmMovie.row.getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (row.getIndex() != aRealmMovie.row.getIndex()) return false;

        return true;
    }

}
