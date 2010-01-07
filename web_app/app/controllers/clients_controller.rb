class ClientsController < ApplicationController

  before_filter do |c|
    contestant = Contestant.find(c.params[:contestant_id])
    c.instance_variable_set :@contestant, contestant
    c.instance_variable_set :@contest, contestant.contest
  end

  access_control do
    default :deny
    allow :administrator
    allow :pupil, :tutor, :teacher, :of => :contestant
  end

  def index
    @clients = @contestant.clients.all(:order => "created_at DESC")

    respond_to do |format|
      format.html # index.html.erb
      format.xml  { render :xml => @clients }
    end
  end

  def show
    @client = @contestant.clients.find(params[:id])

    respond_to do |format|
      format.html { redirect_to contestant_clients_url }
      format.xml  { render :xml => @client }
    end
  end

  def new
    @client = @contestant.clients.build

    respond_to do |format|
      format.html # new.html.erb
      format.xml  { render :xml => @client }
    end
  end

  def edit
    @client = @contestant.clients.find(params[:id])
  end

  def create
    # TODO: http://jimneath.org/2008/05/15/swfupload-paperclip-and-ruby-on-rails/
    @client = @contestant.clients.build(params[:client])
    @client.author = current_user

    success = @client.save
    if success
      begin
        @client.build_index!
      rescue => e
        @client.destroy
        @client = @contestant.clients.build(params[:client])
        @client.errors.add :file, :invalid_zip
        success = false
      end
    end

    respond_to do |format|
      if success
        format.html { redirect_to contestant_clients_url(@contestant) }
        format.xml  { render :xml => @client, :status => :created, :location => @client }
      else
        format.html { render :action => "new" }
        format.xml  { render :xml => @client.errors, :status => :unprocessable_entity }
      end
    end
  end

  def update
    @client = @contestant.clients.find(params[:id])

    respond_to do |format|
      if @client.update_attributes(params[:client])
        flash[:notice] = 'Client was successfully updated.'
        format.html { redirect_to contestant_clients_url(@client) }
        format.xml  { head :ok }
      else
        format.html { render :action => "edit" }
        format.xml  { render :xml => @client.errors, :status => :unprocessable_entity }
      end
    end
  end

  def destroy
    raise "not supported"

    @client = @contestant.clients.find(params[:id])
    @client.destroy

    respond_to do |format|
      format.html { redirect_to(clients_url) }
      format.xml  { head :ok }
    end
  end

  def test
    @client = @contestant.clients.find(params[:id])

    if @client.tested?
      flash[:notice] = "Client wurde bereits getestet."
    else
      @client.test_delayed!
    end

    redirect_to contestant_clients_url(@contestant)
  end

  def browse
    @client = @contestant.clients.find(params[:id])

    if params[:entry_id]
      @entry = @client.file_entries.find(params[:entry_id])
      @entries = @entry.children
    else
      @entries = @client.file_entries.with_level(0)
    end

    render :update do |page|
      # todo empty-check
      page.replace_html '#fileList',
        :partial => "file_entry",
        :collection => @entries,
        :locals => { :client => @client }
    end
  end

  def select_main
    @client = @contestant.clients.find(params[:id])
    @main_entry = @client.file_entries.find(params[:main_id])

    @client.main_file_entry = @main_entry
    @client.save!

    redirect_to contestant_clients_url(@contestant)
  end

  def select
    @client = @contestant.clients.find(params[:id])

    @contestant.current_client = @client
    @contestant.save!

    redirect_to contestant_clients_url(@contestant)
  end

  def status
    @client = @contestant.clients.find(params[:client_id])

    render :update do |page|
      page.replace "client-#{@client.id}",
        :partial => "client", :locals => { :client => @client }
    end
  end

  protected
end