/*******************************************************************************
 * Copyright (c) 2014 itemis AG and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Alexander Nyßen (itemis AG) - initial API and implementation
 *     
 * Note: Parts of this interface have been transferred from org.eclipse.gef.editparts.SimpleRootEditPart.
 * 
 *******************************************************************************/
package org.eclipse.gef4.mvc.parts;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef4.mvc.anchors.IAnchor;
import org.eclipse.gef4.mvc.viewer.IVisualPartViewer;

/**
 * 
 * @author anyssen
 *
 * @param <V>
 */
public abstract class AbstractRootVisualPart<V> extends AbstractVisualPart<V>
		implements IRootVisualPart<V> {

	private IVisualPartViewer<V> viewer;

	public IRootVisualPart<V> getRoot() {
		return this;
	}

	public IVisualPartViewer<V> getViewer() {
		return viewer;
	}

	public IContentPart<V> getRootContentPart() {
		return rootContentPart;
	}

	// TODO: remove this
	private IContentPart<V> rootContentPart;

	/**
	 * @see IRootVisualPart#setContents(EditPart)
	 */
	public void setRootContentPart(IContentPart<V> rootContentPart) {
		if (this.rootContentPart == rootContentPart) {
			return;
		}
		if (this.rootContentPart != null) {
			// unregister
			removeChild(this.rootContentPart);
		}
		this.rootContentPart = rootContentPart;
		if (rootContentPart != null) {
			// register
			addChild(rootContentPart, 0);
		}
	}

	@Override
	public void addHandleParts(List<IHandlePart<V>> handleParts) {
		for (IHandlePart<V> h : handleParts) {
			addChild(h, getChildren().size() - 1);
		}
	}

	@Override
	public void removeHandleParts(List<IHandlePart<V>> handleParts) {
		for (IHandlePart<V> h : handleParts) {
			removeChild(h);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<IHandlePart<V>> getHandleParts() {
		return filterChildren(IHandlePart.class);
	}

	@SuppressWarnings("unchecked")
	private <T extends IVisualPart<V>> List<T> filterChildren(Class<T> type) {
		List<T> handleParts = new ArrayList<T>();
		for (IVisualPart<V> c : getChildren()) {
			if (type.isInstance(c)) {
				handleParts.add((T) c);
			}
		}
		return handleParts;
	}

	// TODO: return two lists (content and handle parts, do not have to provide
	// an on data field for contents parts)

	/**
	 * @see IRootVisualPart#setViewer(EditPartViewer)
	 */
	public void setViewer(IVisualPartViewer<V> newViewer) {
		if (viewer == newViewer)
			return;
		viewer = newViewer;
	}

	@Override
	public void attachVisualToAnchorageVisual(IAnchor<V> anchor) {
		throw new UnsupportedOperationException(
				"IRootVisualPart does not support this");
	}

	@Override
	public void detachVisualFromAnchorageVisual(IAnchor<V> anchor) {
		throw new UnsupportedOperationException(
				"IRootVisualPart does not support this");
	}

	@Override
	protected IAnchor<V> getAnchor(IVisualPart<V> anchored) {
		throw new UnsupportedOperationException(
				"IRootVisualPart does not support this");
	}

}