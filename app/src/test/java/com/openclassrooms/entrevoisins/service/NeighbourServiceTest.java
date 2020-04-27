package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;

import org.hamcrest.collection.IsEmptyCollection;
import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Unit test on Neighbour service
 */
@RunWith(JUnit4.class)
public class NeighbourServiceTest {

    private NeighbourApiService service;

    @Before
    public void setup() {
        service = DI.getNewInstanceApiService();
    }

    @Test
    public void getNeighboursWithSuccess() {
        List<Neighbour> neighbours = service.getNeighbours();
        List<Neighbour> expectedNeighbours = DummyNeighbourGenerator.DUMMY_NEIGHBOURS;
        assertThat(neighbours, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedNeighbours.toArray()));
    }

    @Test
    public void deleteNeighbourWithSuccess() {
        Neighbour neighbourToDelete = service.getNeighbours().get(0);
        service.deleteNeighbour(neighbourToDelete);
        assertFalse(service.getNeighbours().contains(neighbourToDelete));
    }

    @Test
    public void updateNeighbourWithSuccess() {
        Neighbour updateNeighbourToFavorite = service.getNeighbours().get(0);
        assertFalse(updateNeighbourToFavorite.isFavorite());
        service.updateNeighbour(0);
        assertTrue(updateNeighbourToFavorite.isFavorite());
        service.updateNeighbour(0);
        assertFalse(updateNeighbourToFavorite.isFavorite());
    }

    @Test
    public void getFavoritesWithSuccess() {
        List<Neighbour> favoriteNeighbours = service.getFavorites();
        assertThat(favoriteNeighbours, IsEmptyCollection.empty());
        Neighbour updateNeighbourToFavorite = service.getNeighbours().get(0);
        assertFalse(updateNeighbourToFavorite.isFavorite());
        service.updateNeighbour(0);
        favoriteNeighbours = service.getFavorites();
        assertThat(favoriteNeighbours.size(), is(1));
        assertTrue(favoriteNeighbours.contains(updateNeighbourToFavorite));
        service.updateNeighbour(0);
        favoriteNeighbours = service.getFavorites();
        assertThat(favoriteNeighbours.size(), is(0));
        assertFalse(favoriteNeighbours.contains(updateNeighbourToFavorite));
    }
}
