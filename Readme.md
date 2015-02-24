## Readme

### Design decisions

About the **behaviours** implemented for this homework assignment, both *intersection of ray and linesegment* and *intersection of two line segments* were basically implemented at LineSegment class. But the entire behavior implementation was split with a object *GeometryUtils* that generates equation for Lines in the form Ax + By = C. Based on the line equations, the **intersect** method - on the **LineSegment** class - is capable of determine whether two lines intersect and if the intersection point lies on the segment.
I decided to split the behavior because I understand that the process of getting the line equation from two points are not unique to this behavior and, moreover, is this way, the method has only one responsibility.

To *obtain the list of line segments of a polygon* I created a method that iterates over the point list of each polygon and creates LineSegments for each consecutive pair of points.
To *discover whether a point lies inside a polygon* I divide the into two steps:
the **first** is to determine if the point belongs to any LineSegment of the Polygon, and the **second** is to use ray shooting technique.
The *bounding box* is computed by an object - which has a match case to cover all possible implementations of the shapes trait. This makes the code concise and easier to maintain.

Finally, the *Monte Carlo method* consists of a **area** method in the trait Shape that is abstract - so it must be implemented by every Shape implementation - and this allows each polygon to deal with their own area in particular. However, we can make use of an object - as we did for bounding box - to concentrate this specific behavior and easily extent it when necessary, for instance, adding a more precise method of calculation.
