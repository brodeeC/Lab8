package components;

import input.visitor.ComponentNodeVisitor;

public interface ComponentNode
{
	Object accept(ComponentNodeVisitor visitor, Object o);
}
