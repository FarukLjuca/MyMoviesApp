package com.atlantbh.mymoviesapp.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.atlantbh.mymoviesapp.R;
import com.atlantbh.mymoviesapp.adapters.SearchAdapter;
import com.atlantbh.mymoviesapp.api.SearchAPI;
import com.atlantbh.mymoviesapp.helpers.AppHelper;
import com.atlantbh.mymoviesapp.helpers.AppString;
import com.atlantbh.mymoviesapp.model.Search;
import com.atlantbh.mymoviesapp.model.SearchResult;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class SearchActivity extends AppCompatActivity {
    @Bind(R.id.tbSearchToolbar)
    Toolbar toolbar;
    @Bind(R.id.lvSearchListView)
    ListView listView;
    @Bind(R.id.tvSearchNoResults)
    TextView noResults;

    public Context getContext() {
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }

        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.itSearch).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(SearchActivity.this, MovieListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);

            Retrofit retrofit = AppHelper.getRetrofit();
            final SearchAPI searchAPI = retrofit.create(SearchAPI.class);

            Call<SearchResult> call = searchAPI.getSearchResult(query);
            call.enqueue(new Callback<SearchResult>() {
                @Override
                public void onResponse(Response<SearchResult> response, Retrofit retrofit) {
                    SearchResult searchResult = response.body();
                    if (searchResult.getSearchList().size() > 0) {
                        final SearchAdapter searchAdapter = new SearchAdapter(getContext(), searchResult.getSearchList());
                        listView.setAdapter(searchAdapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Search search = (Search) searchAdapter.getItem(position);
                                Intent intent = null;
                                switch (search.getMediaType()) {
                                    case AppString.MOVIE:
                                        intent = new Intent(getContext(), DetailsActivity.class);
                                        intent.putExtra(AppString.MOVIE_ID, search.getId());
                                        startActivity(intent);
                                        break;
                                    case AppString.TV:
                                        intent = new Intent(getContext(), DetailsActivity.class);
                                        intent.putExtra(AppString.TV_ID, search.getId());
                                        startActivity(intent);
                                        break;
                                    case AppString.PERSON:
                                        intent = new Intent(getContext(), ActorActivity.class);
                                        intent.putExtra(AppString.ACTOR_ID, search.getId());
                                        startActivity(intent);
                                        break;
                                }
                            }
                        });
                    }
                    else {
                        listView.setVisibility(View.GONE);
                        noResults.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(Throwable t) {

                }
            });
        }
    }
}
