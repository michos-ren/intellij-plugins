// Copyright 2000-2019 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package org.jetbrains.vuejs.lang

import com.intellij.openapi.actionSystem.IdeActions
import com.intellij.testFramework.fixtures.BasePlatformTestCase

class VueCopyPasteTest : BasePlatformTestCase() {
  override fun getBasePath(): String = ""
  override fun getTestDataPath(): String = getVueTestDataPath() + "/copyPaste"

  private fun doTest() {
    myFixture.copyDirectoryToProject(getTestName(true), ".")
    myFixture.configureFromTempProjectFile(getTestName(false) + ".vue")
    myFixture.performEditorAction(IdeActions.ACTION_EDITOR_COPY)
    myFixture.configureFromTempProjectFile("Destination.vue")
    myFixture.performEditorAction(IdeActions.ACTION_EDITOR_PASTE)
    myFixture.checkResultByFile(getTestName(true) + "/Destination_after.vue")
  }

  fun testSimpleWithImports() {
    doTest()
  }

  fun testSimpleWithNoImports() {
    doTest()
  }

  fun testSimpleWithNoImportsBindingContext() {
    doTest()
  }

}