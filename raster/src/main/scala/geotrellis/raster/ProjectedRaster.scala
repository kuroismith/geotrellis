/*
 * Copyright 2016 Azavea
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package geotrellis.raster

import geotrellis.proj4.CRS
import geotrellis.vector.{Extent, ProjectedExtent}


/**
  * The companion object for the [[ProjectedRaster]] type.
  */
object ProjectedRaster {
  /**
    * Implicit conversion from a [[Raster]], CRS pair to a
    * [[ProjectedRaster]].
    */
  @deprecated("Implicit conversions considered unsafe", "2.1.1")
  implicit def tupToRaster[T <: CellGrid](tup: (Raster[T], CRS)): ProjectedRaster[T] =
    ProjectedRaster(tup._1, tup._2)

  /**
    * Implicit conversion from a [[ProjectedRaster]] to a [[Raster]].
    */
  @deprecated("Implicit conversions considered unsafe", "2.1.1")
  implicit def projectedToRaster[T <: CellGrid](p: ProjectedRaster[T]): Raster[T] =
    p.raster

  /**
    * Implicit conversion from a [[ProjectedRaster]] to a tile.
    */
  @deprecated("Implicit conversions considered unsafe", "2.1.1")
  implicit def projectedToTile[T <: CellGrid](p: ProjectedRaster[T]): T =
    p.raster.tile

  /**
    * Take a [[Tile]], and Extent, and a CRS and use them to produce a
    * [[ProjectedRaster]].
    */
  def apply[T <: CellGrid](tile: T, extent: Extent, crs: CRS): ProjectedRaster[T] =
    ProjectedRaster(Raster(tile, extent), crs)

  /**
    * Take a [[Tile]], and ProjectedExtent, and a CRS and use them to produce a
    * [[ProjectedRaster]].
    */
  def apply[T <: CellGrid](tile: T, extent: ProjectedExtent): ProjectedRaster[T] =
    ProjectedRaster(Raster(tile, extent.extent), extent.crs)
}

/**
  * The [[ProjectedRaster]] type.
  */
case class ProjectedRaster[T <: CellGrid](raster: Raster[T], crs: CRS) {
  def tile: T = raster.tile
  def extent: Extent = raster.extent
  def projectedExtent: ProjectedExtent = ProjectedExtent(extent, crs)
  def cols: Int = raster.cols
  def rows: Int = raster.rows
  def mapTile[A <: CellGrid](f: T => A): ProjectedRaster[A] = this.copy(raster = raster.mapTile(f))
}
