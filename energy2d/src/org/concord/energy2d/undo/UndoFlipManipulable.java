package org.concord.energy2d.undo;

import java.awt.Shape;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import org.concord.energy2d.math.Blob2D;
import org.concord.energy2d.math.TransformableShape;
import org.concord.energy2d.model.Manipulable;
import org.concord.energy2d.model.Model2D;
import org.concord.energy2d.model.Part;
import org.concord.energy2d.view.View2D;

public class UndoFlipManipulable extends AbstractUndoableEdit {

	private static final long serialVersionUID = 1L;
	private Manipulable selectedManipulable;
	private View2D view;
	private Model2D model;
	private String name;
	private int flipDirection;

	public UndoFlipManipulable(View2D view, int flipDirection) {
		this.view = view;
		this.flipDirection = flipDirection;
		model = view.getModel();
		selectedManipulable = view.getSelectedManipulable();
		if (selectedManipulable instanceof Part) {
			name = "Part";
		}
	}

	@Override
	public void undo() throws CannotUndoException {
		super.undo();
		if (selectedManipulable instanceof Part) {
			Shape shape = selectedManipulable.getShape();
			if (shape instanceof TransformableShape) {
				TransformableShape s = (TransformableShape) shape;
				switch (flipDirection) {
				case 0:
					s.flipX();
					break;
				case 1:
					s.flipY();
					break;
				}
				if (s instanceof Blob2D) {
					((Blob2D) s).update();
				}
				model.refreshPowerArray();
				model.refreshTemperatureBoundaryArray();
				model.refreshMaterialPropertyArrays();
				if (view.isViewFactorLinesOn())
					model.generateViewFactorMesh();
			}
		}
		view.setSelectedManipulable(selectedManipulable);
		view.repaint();
	}

	@Override
	public void redo() throws CannotRedoException {
		super.redo();
		if (selectedManipulable instanceof Part) {
			Shape shape = selectedManipulable.getShape();
			if (shape instanceof TransformableShape) {
				TransformableShape s = (TransformableShape) shape;
				switch (flipDirection) {
				case 0:
					s.flipX();
					break;
				case 1:
					s.flipY();
					break;
				}
				if (s instanceof Blob2D) {
					((Blob2D) s).update();
				}
				model.refreshPowerArray();
				model.refreshTemperatureBoundaryArray();
				model.refreshMaterialPropertyArrays();
				if (view.isViewFactorLinesOn())
					model.generateViewFactorMesh();
			}
		}
		view.setSelectedManipulable(selectedManipulable);
		view.repaint();
	}

	@Override
	public String getPresentationName() {
		return "Flip " + (name == null ? "Manipulable" : name);
	}

}
