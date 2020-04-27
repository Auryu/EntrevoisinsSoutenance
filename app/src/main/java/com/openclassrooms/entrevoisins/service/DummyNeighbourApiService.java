package com.openclassrooms.entrevoisins.service;

import android.support.v7.widget.LinearLayoutManager;

import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.ui.neighbour_list.MyNeighbourRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Dummy mock for the Api
 */
public class DummyNeighbourApiService implements  NeighbourApiService {

    private List<Neighbour> neighbours = DummyNeighbourGenerator.generateNeighbours();

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Neighbour> getNeighbours() { return neighbours; }

    @Override
    public List<Neighbour> getFavorites() {
        List<Neighbour> favoriteNeighbours = new ArrayList<>();
        for (Neighbour neighbour : neighbours){
            if (neighbour.isFavorite()){
                favoriteNeighbours.add(neighbour);
            }
        }
        return favoriteNeighbours;
    }

    @Override
    public void updateNeighbour(int position){
        neighbours.get(position).setFavorite(!neighbours.get(position).isFavorite());
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteNeighbour(Neighbour neighbour) {
        neighbours.remove(neighbour);
    }

}

