package com.trip.server.overpass.query;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class QueryTest {

    private final static QueryBuilder queryBuilder = new QueryBuilder(List.of("default-area"));

    @Test
    public void testEmptyQuery() {
        var query = queryBuilder.json()
                .out();
        assertEquals("[out:json];out;", query);
    }

    @Test
    public void testAreaWithIdsQuery() {
        var query = queryBuilder.json()
                .area(Area.withIds(1L, 2L))
                .out();
        assertEquals("[out:json];area(id:1,2)->.a;out;", query);
    }

    @Test
    public void testAreaWithTagsQuery() {
        var query = queryBuilder.json()
                .area(Area.withTags("'name'", "'another-tag'"))
                .out();
        assertEquals("[out:json];area['name']['another-tag']->.a;out;", query);
    }

    @Test
    public void testAreaWithIdsAndTagsQuery() {
        var query = queryBuilder.json()
                .area(Area.withIds(1L, 2L).tags("tag"))
                .out();
        assertEquals("[out:json];area[tag](id:1,2)->.a;out;", query);
    }

    @Test
    public void testDefaultAreaQuery() {
        var query = queryBuilder.json()
                .defaultArea()
                .out();
        assertEquals("[out:json];area[default-area]->.a;out;", query);
    }

    @Test
    public void testSetWithIdsQuery() {
        var query = queryBuilder.json()
                .set(Node.withIds(1L, 2L))
                .out();
        assertEquals("[out:json];node(id:1,2);out;", query);
    }

    @Test
    public void testSetWithTagsQuery() {
        var query = queryBuilder.json()
                .set(Node.withIds(1L, 2L))
                .out();
        assertEquals("[out:json];node(id:1,2);out;", query);
    }

    @Test
    public void testSetWithIdsAndTagsQuery() {
        var query = queryBuilder.json()
                .set(Node.withIds(1L, 2L).tags("tag"))
                .out();
        assertEquals("[out:json];node[tag](id:1,2);out;", query);
    }

    @Test
    public void testFilterChangeQuery() {
        var query = queryBuilder.json()
                .set(Node.withIds(1L, 2L).area())
                .out();
        assertEquals("[out:json];node(area.a);out;", query);
    }

    @Test
    public void testAreaAndSetQuery() {
        var query = queryBuilder.json()
                .defaultArea()
                .set(Node.withArea())
                .out();
        assertEquals("[out:json];area[default-area]->.a;node(area.a);out;", query);
    }

    @Test
    public void testMultiSetQuery() {
        var query = queryBuilder.json()
                .set(Node.withArea())
                .set(Way.withArea())
                .out();
        assertEquals("[out:json];(node(area.a);way(area.a););out;", query);
    }

    @Test
    public void testBoundingBoxQuery() {
        var query = queryBuilder.json()
                .boundingBox(1d, 2d, -1d, -2d)
                .out();
        assertEquals("[out:json][bbox:1.0,2.0,-1.0,-2.0];out;", query);
    }

    @Test
    public void testOutQuery() {
        assertEquals("[out:json];out ids;", queryBuilder.json().outIds());
        assertEquals("[out:json];out skel;", queryBuilder.json().outSkel());
        assertEquals("[out:json];out body;", queryBuilder.json().outBody());
        assertEquals("[out:json];out tags;", queryBuilder.json().outTags());
        assertEquals("[out:json];out meta;", queryBuilder.json().outMeta());
    }

}
