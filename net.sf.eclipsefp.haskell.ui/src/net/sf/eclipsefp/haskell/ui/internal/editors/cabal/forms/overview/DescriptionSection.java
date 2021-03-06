// Copyright (c) 2008 by Leif Frenzel - see http://leiffrenzel.de
// Copyright (c) 2011 by Alejandro Serrano
// This code is made available under the terms of the Eclipse Public License,
// version 1.0 (EPL). See http://www.eclipse.org/legal/epl-v10.html
package net.sf.eclipsefp.haskell.ui.internal.editors.cabal.forms.overview;

import net.sf.eclipsefp.haskell.core.cabalmodel.CabalSyntax;
import net.sf.eclipsefp.haskell.ui.internal.editors.cabal.CabalFormEditor;
import net.sf.eclipsefp.haskell.ui.internal.editors.cabal.forms.CabalFormSection;
import net.sf.eclipsefp.haskell.ui.internal.util.UITexts;
import org.eclipse.core.resources.IProject;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.editor.IFormPage;
import org.eclipse.ui.forms.widgets.FormToolkit;

/**
 * <p>
 * form section for description info (synopsis, description ...).
 * </p>
 *
 * @author Leif Frenzel
 */
class DescriptionSection extends CabalFormSection {

  DescriptionSection( final IFormPage page, final Composite parent,
      final CabalFormEditor editor, final IProject project ) {
    super( page, parent, editor, UITexts.descriptionSection_title, project );
  }

  @Override
  protected void createClient( final FormToolkit toolkit ) {
    Composite container = toolkit.createComposite( getSection() );
    container.setLayout( new GridLayout( 2, false ) );
    GridData data = new GridData( GridData.FILL_BOTH );
    getSection().setLayoutData( data );

    String text = UITexts.descriptionSection_entrySynopsis;
    createFormEntry( CabalSyntax.FIELD_SYNOPSIS, toolkit, container, text );
    String text2 = UITexts.descriptionSection_entryDescription;
    createMultiLineFormEntry( CabalSyntax.FIELD_DESCRIPTION, toolkit,
        container, text2 );
    String text3 = UITexts.descriptionSection_entryHomepage;
    createFormEntry( CabalSyntax.FIELD_HOMEPAGE, toolkit, container, text3 );
    String text4 = UITexts.descriptionSection_entryCategory;
    createComboFormEntry( CabalSyntax.FIELD_CATEGORY, new CategoryChoice(),
        toolkit, container, text4 );
    toolkit.paintBordersFor( container );
    getSection().setClient( container );
  }
}
