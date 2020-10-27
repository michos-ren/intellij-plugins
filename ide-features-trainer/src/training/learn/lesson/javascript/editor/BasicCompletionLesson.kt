// Copyright 2000-2020 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package training.learn.lesson.javascript.editor

import training.lang.JavaScriptLangSupport
import training.learn.LessonsBundle
import training.learn.interfaces.Module
import training.learn.lesson.javascript.setLanguageLevel
import training.learn.lesson.javascript.textBeforeCaret
import training.learn.lesson.kimpl.KLesson
import training.learn.lesson.kimpl.LessonContext
import training.learn.lesson.kimpl.parseLessonSample

class BasicCompletionLesson(module: Module)
  : KLesson("The Nuts and Bolts of Code Completion", LessonsBundle.message("js.editor.completion.title"), module,
            JavaScriptLangSupport.lang) {

  val sample = parseLessonSample("""
        let favoriteAnimals = ['dog', 'cat', 'unicorn'];
        
        function pickAnimal(arr) {
            const rnd = arr.length * Math.random();
            return arr[<caret>];
        }
        
        console.log();
        """.trimIndent())


  override val lessonContent: LessonContext.() -> Unit
    get() {
      return {
        setLanguageLevel()
        prepareSample(sample)

        caret(136)
        task("EditorChooseLookupItem") {
          text(LessonsBundle.message("js.editor.completion.choose.lookup", strong("Ma"), action("EditorChooseLookupItem"),
                                     code("Math")))
          trigger(it) {
            textBeforeCaret("Math")
          }
        }

        task("EditorChooseLookupItem") {
          text(LessonsBundle.message("js.editor.completion.choose.method",
                                     action("EditorEnter"), action("EditorTab"), code("."), code("Math"), strong("f"), code("floor")))
          trigger(it) {
            textBeforeCaret("Math.floor(")
          }
        }

        task("QuickJavaDoc") {
          text(
            LessonsBundle.message("js.editor.completion.parameter.info",
                                  action("ParameterInfo"), code("()"), action(it)))
          stateCheck {
            val line = editor.caretModel.logicalPosition.line
            val column = editor.caretModel.logicalPosition.column
            line == 4 && column in 20..26
          }
          trigger(it)
        }
        task {
          text(LessonsBundle.message("js.editor.completion.add.parameter",
                                     code("rnd"), code("()")))
          stateCheck {
            textBeforeCaret("Math.floor(rnd")
          }
        }
        task("EditorChooseLookupItem") {
          text(
            LessonsBundle.message("js.editor.completion.console.log.argument",
                                  code("console.log"), code("()"), code("pickAnimal(favoriteAnimals)")))
          trigger(it) {
            textBeforeCaret("pickAnimal(favoriteAnimals")
          }
        }
        text(LessonsBundle.message("js.editor.completion.next", action("learn.next.lesson")))
      }
    }
  override val existedFile = "basicCompletion.js"

}


