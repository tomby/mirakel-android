/*******************************************************************************
 * Mirakel is an Android App for managing your ToDo-Lists
 *
 *  Copyright (c) 2013-2014 Anatolij Zelenin, Georg Semmler.
 *
 *      This program is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      any later version.
 *
 *      This program is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU General Public License for more details.
 *
 *      You should have received a copy of the GNU General Public License
 *      along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package de.azapps.mirakel.new_ui.fragments;


import android.app.Activity;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.ListView;

import de.azapps.mirakel.adapter.OnItemClickedListener;
import de.azapps.mirakel.model.list.ListMirakel;
import de.azapps.mirakelandroid.R;
import de.azapps.mirakel.new_ui.adapter.ListAdapter;


public class ListsFragment extends ListFragment implements LoaderManager.LoaderCallbacks {

    private ListAdapter mAdapter;
    private OnItemClickedListener<ListMirakel> mListener;



    public ListsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAdapter = new ListAdapter(getActivity(), null, 0);
        getListView().setDivider(new ColorDrawable(getResources().getColor(R.color.transparent)));
        setListAdapter(mAdapter);
        getLoaderManager().initLoader(0, null, this);
        getListView().setBackgroundColor(getResources().getColor(R.color.white));
    }


    @Override
    public void onListItemClick (ListView l, View v, int position, long id) {
        mListener.onItemSelected(((ListAdapter.ViewHolder) v.getTag()).getList());
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnItemClickedListener<ListMirakel>) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnListSelectedListener");
        }
    }



    @Override
    public Loader onCreateLoader(int i, Bundle bundle) {
        return ListMirakel.allWithSpecialSupportCursorLoader();
    }

    @Override
    public void onLoadFinished(Loader loader, Object o) {
        mAdapter.swapCursor((Cursor) o);
    }

    @Override
    public void onLoaderReset(Loader loader) {
        mAdapter.swapCursor(null);
    }
}
