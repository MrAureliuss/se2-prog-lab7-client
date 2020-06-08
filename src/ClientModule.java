import Client.DecryptingImp;
import Client.ReceiverImp;
import Client.SenderImp;
import Client.SessionImp;
import Commands.Command;
import Commands.CommandInvokerImp;
import Commands.CommandReceiverImp;
import Commands.ConcreteCommands.*;
import Commands.Utils.Creaters.ElementCreatorImp;
import Commands.Utils.HashEncrypterImp;
import Commands.Utils.Readers.EnumReaders.*;
import Commands.Utils.Readers.PrimitiveAndReferenceReaders.*;
import Interfaces.*;
import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;


/**
 * Клиентский модуль для Guice Dependency Injection.
 */
public class ClientModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(ConsoleManager.class).to(ConsoleManagerImp.class);
        bind(CommandInvoker.class).to(CommandInvokerImp.class);
        bind(ElementCreator.class).to(ElementCreatorImp.class);
        bind(Sender.class).to(SenderImp.class);
        bind(Session.class).to(SessionImp.class);
        bind(LoginPassReader.class).to(LoginPassReaderImp.class);
        bind(CommandReceiver.class).to(CommandReceiverImp.class);
        bind(Receiver.class).to(ReceiverImp.class);
        bind(Decrypting.class).to(DecryptingImp.class);
        bind(HashEncrypter.class).to(HashEncrypterImp.class);
        bind(PrimitiveFloatReader.class).to(PrimitiveFloatReaderImp.class);
        bind(PrimitiveIntReader.class).to(PrimitiveIntReaderImp.class);
        bind(RefIntReader.class).to(RefIntReaderImp.class);
        bind(StringReader.class).to(StringReaderImp.class);
        bind(ColorReader.class).to(ColorReaderImp.class);
        bind(CountryReader.class).to(CountryReaderImp.class);
        bind(FormOfEducationReader.class).to(FormOfEducationReaderImp.class);
        bind(SemesterReader.class).to(SemesterReaderImp.class);


        Multibinder<Command> commandBinder = Multibinder.newSetBinder(binder(), Command.class);
        commandBinder.addBinding().to(Add.class);
        commandBinder.addBinding().to(Clear.class);
        commandBinder.addBinding().to(CountByGroupAdmin.class);
        commandBinder.addBinding().to(ExecuteScript.class);
        commandBinder.addBinding().to(Exit.class);
        commandBinder.addBinding().to(MaxByGroupAdmin.class);
        commandBinder.addBinding().to(Help.class);
        commandBinder.addBinding().to(MinBySemesterEnum.class);
        commandBinder.addBinding().to(Info.class);
        commandBinder.addBinding().to(RemoveLower.class);
        commandBinder.addBinding().to(RemoveGreater.class);
        commandBinder.addBinding().to(RemoveByID.class);
        commandBinder.addBinding().to(Show.class);
        commandBinder.addBinding().to(Head.class);
        commandBinder.addBinding().to(Update.class);
    }
}
